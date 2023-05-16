package com.homevision.photosdownloader.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.homevision.photosdownloader.core.House;
import com.homevision.photosdownloader.core.HousesData;

@ExtendWith(MockitoExtension.class)
public class HousesDataDownloaderImplTest {

    private static final String DUMMY_URL = "http://localhost:9000/dummy";
    private static final String DUMMY_RESPONSE = "{'response': 'dummy'}";

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResp;

    @Mock
    private HousesDataParser parser;

    private HousesDataDownloaderImpl impl;

    @BeforeEach
    void before() throws IOException, InterruptedException {
        reset(mockHttpResp, parser);
        impl = spy(new HousesDataDownloaderImpl(parser, mockHttpClient));
    }

    @Test
    void testDownloadSinglePage() {
        var houses = Arrays.asList(
            House.builder().id(1L).build(),
            House.builder().id(2L).build(),
            House.builder().id(3L).build(),
            House.builder().id(4L).build()
        );
        var data = HousesData.builder().houses(houses).ok(true).build();
        var last = HousesData.builder().houses(Collections.emptyList()).ok(true).build();
        doReturn(data, last).when(impl).downloadPage(anyString(), anyInt(), anyInt());
        var result = impl.download(DUMMY_URL, 1, 4);
        assertEquals(houses, result);

    }

    @Test
    void testDownloadMultiPage() {
        var houses1 = Arrays.asList(
            House.builder().id(1L).build(),
            House.builder().id(2L).build(),
            House.builder().id(3L).build(),
            House.builder().id(4L).build()
        );
        var houses2 = Arrays.asList(
            House.builder().id(5L).build(),
            House.builder().id(6L).build(),
            House.builder().id(7L).build(),
            House.builder().id(8L).build()
        );
        var all = new ArrayList<>() { { addAll(houses1); addAll(houses2); } };
        var data = HousesData.builder().houses(houses1).ok(true).build();
        var data2 = HousesData.builder().houses(houses2).ok(true).build();
        var last = HousesData.builder().houses(Collections.emptyList()).ok(true).build();
        doReturn(data, data2, last).when(impl).downloadPage(anyString(), anyInt(), anyInt());
        var result = impl.download(DUMMY_URL, 10, 4);
        assertEquals(all, result);

    }

    @Test
    void testDownloadPage() throws IOException {
        var houses = Arrays.asList(
            House.builder().build(),
            House.builder().build(),
            House.builder().build(),
            House.builder().build()
        );
        var data = HousesData.builder().houses(houses).ok(true).build();
        doReturn(data).when(impl).downloadPageAttempt(anyString(), anyInt(), anyInt());
        HousesData page = impl.downloadPage(DUMMY_URL, 1, 4);
        assertEquals(data, page);
    }

    @Test
    void testDownloadPageWithRetry() throws IOException {
        var houses = Arrays.asList(
            House.builder().build(),
            House.builder().build(),
            House.builder().build(),
            House.builder().build()
        );
        var data = HousesData.builder().houses(houses).ok(true).build();
        doReturn(HousesData.FAILED_DATA, data).when(impl).downloadPageAttempt(anyString(), anyInt(), anyInt());
        HousesData page = impl.downloadPage(DUMMY_URL, 1, 4);
        assertEquals(data, page);
    }

    @Test
    void testBuildFetchUrl() {
        assertEquals("http://localhost:9000/dummy?page=1&per_page=10", impl.buildFetchUrl(DUMMY_URL, 1, 10));
        assertEquals("http://localhost:9000/dummy?page=2&per_page=10", impl.buildFetchUrl(DUMMY_URL, 2, 10));
    }

    @Test
    void testDownloadPageAttempt() throws Exception {
        HousesData emptyOk = HousesData.builder()
            .ok(true)
            .houses(Collections.emptyList())
            .build();
        doReturn(Optional.of(DUMMY_RESPONSE), Optional.of(DUMMY_RESPONSE), Optional.empty()).when(impl).fetchPage(anyString());
        doReturn(emptyOk, emptyOk).when(parser).parse(any());

        assertEquals(emptyOk, impl.downloadPageAttempt(DUMMY_URL, 1, 4));
        assertEquals(emptyOk, impl.downloadPageAttempt(DUMMY_URL, 1, 4));
        assertEquals(HousesData.FAILED_DATA, impl.downloadPageAttempt(DUMMY_URL, 1, 4));

        verify(parser, times(2)).parse(any());
    }

    @Test
    void testFetchPageWithOkData() throws IOException, InterruptedException {
        doReturn(mockHttpResp).when(mockHttpClient).send(any(), any());
        doReturn(200).when(mockHttpResp).statusCode();
        doReturn(DUMMY_RESPONSE).when(mockHttpResp).body();
        var resultOk = impl.fetchPage(DUMMY_URL);
        assertTrue(resultOk.isPresent(), "Result should contain a valid data");
        assertEquals(resultOk.get(), DUMMY_RESPONSE);
    }
    
    @Test
    void testFetchPageWithFailedData() throws IOException, InterruptedException {
        doReturn(mockHttpResp).when(mockHttpClient).send(any(), any());
        doReturn(500).when(mockHttpResp).statusCode();
        doReturn(DUMMY_RESPONSE).when(mockHttpResp).body();
        var resultFailedFetch = impl.fetchPage(DUMMY_URL);
        assertFalse(resultFailedFetch.isPresent(), "Result should not contain a valid data");
        assertTrue(resultFailedFetch.isEmpty());

    }

}
