package com.homevision.photosdownloader.io;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.homevision.photosdownloader.core.House;
import com.homevision.photosdownloader.core.HousesData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HousesDataDownloaderImpl implements HousesDataDownloader {

    private static final int MAX_ATTEMPTS = 3;

    private final HousesDataParser housesDataParser;
    private final HttpClient httpClient;

    public HousesDataDownloaderImpl(final HousesDataParser housesDataParser, final HttpClient httpClient) {
        this.housesDataParser = housesDataParser;
        this.httpClient = httpClient;
    }

    @Override
    public List<House> download(final String baseUrl, final int pages, final int pageSize) {
        var result = new ArrayList<House>();
        var hasNext = true;
        for(int page = 1; hasNext && page <= pages; page++) {
            hasNext = false;
            var data = downloadPage(baseUrl, page, pageSize);
            if (data.getOk()) {
                List<House> houses = data.getHouses();
                hasNext = (houses.size() >= pageSize);
                result.addAll(houses);

            }
        }
        return result;
    }

    protected HousesData downloadPage(final String baseUrl, final int page, final int pageSize) {
        var result = downloadPageAttempt(baseUrl, page, pageSize);
        for (int attempt = 1; shouldRetry(result, attempt); attempt++) {
            result = downloadPageAttempt(baseUrl, page, pageSize);
        }
        return result;
    }

    protected HousesData downloadPageAttempt(final String baseUrl, final int page, final int pageSize) {
        var fetchPageUrl = buildFetchUrl(baseUrl, page, pageSize);
        var result = fetchPage(fetchPageUrl);
        if (result.isPresent()) {
            return this.housesDataParser.parse(result.get());
        } else {
            return HousesData.FAILED_DATA;
        }
    }

    protected Optional<String> fetchPage(final String fetchPageUrl) {
        try {
            var request = HttpRequest.newBuilder().uri(new URI(fetchPageUrl)).GET().build();
            var response = httpClient.send(request, BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                log.error("Fetched url '{}'", fetchPageUrl);
                return Optional.of(response.body());
            } else {
                log.error("Failed to fetch from url '{}'. Status: {}, Message: {}", fetchPageUrl, response.statusCode(),
                        response.body());
            }
        } catch (Exception e) {
            log.error("Failed to fetch from url '{}'", fetchPageUrl, e);
        }
        return Optional.empty();
    }

    protected boolean shouldRetry(final HousesData lastResult, final int attempt) {
        if (!lastResult.getOk() && attempt < MAX_ATTEMPTS) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(1));
                return true;
            } catch (InterruptedException _ignored) {
                return false;
            }
        }
        return false;
    }

    protected String buildFetchUrl(final String baseUrl, final int page, final int pageSize) {
        return "%s?page=%s&per_page=%s".formatted(baseUrl, page, pageSize);
    }
}
