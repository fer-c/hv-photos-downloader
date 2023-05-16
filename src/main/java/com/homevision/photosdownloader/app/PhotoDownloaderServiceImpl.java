package com.homevision.photosdownloader.app;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import com.homevision.photosdownloader.core.House;
import com.homevision.photosdownloader.io.HousesDataDownloader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PhotoDownloaderServiceImpl implements PhotoDownloaderService {

    private final HousesDataDownloader downloader;

    public PhotoDownloaderServiceImpl(final HousesDataDownloader downloader) {
        this.downloader = downloader;
    }

    @Override
    public void downloadPhotos(final String baseUrl, final int pages, final int pageSize, final File targetDir) {
        var houses = downloader.download(baseUrl, pages, pageSize);
        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (House house: houses) {
            pool.submit(() -> { downloadPhoto(house, targetDir); });
        }
        pool.shutdown();
        try {
            pool.awaitTermination(15L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            log.error("Failed to download photos", e);
            System.err.println("Failed to download ");
        }
        
    }

    protected void downloadPhoto(final House house, final File targetDir) {
        String ext = extractFileExtension(house.getPhotoURL());
        String normalizedAddress = normalizeAddress(house.getAddress());
        String destPath = "%s-%s.%s".formatted(house.getId(), normalizedAddress, ext);
        File destFile = new File(targetDir, destPath);
        String dest = destFile.getPath();
        URL url;
        try {
            url = new URL(house.getPhotoURL());
        } catch (MalformedURLException e) {
            log.error("Failed to downloaded {} to ", house.getPhotoURL(), dest, e);
            System.err.println("Failed to download " + house.getPhotoURL() + ". Reason: Invalid URL");
            return;
        }
        try(InputStream inputStream = url.openStream()) {
            FileOutputStream fileOS = new FileOutputStream(destFile);
            IOUtils.copy(inputStream, fileOS);            
        } catch (Exception e) {
            log.error("Failed to downloaded {} to ", house.getPhotoURL(), dest, e);
            System.err.println("Failed to download " + house.getPhotoURL());
            return;
        }
        System.out.println("Downloaded " + house.getPhotoURL() + " to " + dest);
    }

    private String normalizeAddress(final String address) {
        return address.replaceAll("[^a-zA-Z0-9]", "");
    }

    private String extractFileExtension(final String photoURL) {
        var p = Pattern.compile("\\.([^.]+)$");
        var m = p.matcher(photoURL);
        if(m.find()) {
            return m.group(1);
        }
        return "";
    }
}
