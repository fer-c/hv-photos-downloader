package com.homevision.photosdownloader.io;

import com.homevision.photosdownloader.core.HousesData;

/**
 * Parse that transforms the json text into 
 */
public interface HousesDataParser {
    /**
     * Parses JSON string into HousesData instance
     */
    HousesData parse(String text);
}
