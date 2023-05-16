package com.homevision.photosdownloader.io;

import java.util.List;

import com.homevision.photosdownloader.core.House;

/**
 * Fetches data from the House data service
 */
public interface HousesDataDownloader {
    /**
     * Downloads the House Data information using HomeVision API
     * @param baseUrl Base URL for the download API
     * @param pages max total number of pages to fetch
     * @param pageSize page size
     * @return the list of houses recovered from the service. Empty list otherwise
     */
    List<House> download(String baseUrl, int pages, int pageSize);
}
