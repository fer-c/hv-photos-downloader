package com.homevision.photosdownloader.app;

import java.io.File;

public interface PhotoDownloaderService {
    /**
     * Starts the download of the of photos referenced in the API.
     * @param baseUrl Base URL for the download API
     * @param pages Total number of pages to fetch
     * @param pageSize number of records per page
     * @param targetDir Target directory to store the downloaded photos
     */
    void downloadPhotos(String baseUrl, int pages, int pageSize, File targetDir);
}
