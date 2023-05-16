package com.homevision.photosdownloader.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

public class HousesDataParserImplTest {
    @Test
    void testParseOk() throws IOException {
        var parser = new HousesDataParserImpl();
        String okData = IOUtils.toString(new ClassPathResource("sample-houses-ok.json").getInputStream(),
                Charset.defaultCharset());
        var result = parser.parse(okData);
        assertNotNull(result);
        assertTrue(result.getOk());
        assertNull(result.getMessage());
        assertFalse(result.getHouses().isEmpty());
        assertEquals(5, result.getHouses().size());
    }

    @Test
    void testParseNotOk() throws IOException {
        var parser = new HousesDataParserImpl();
        String failedData = IOUtils.toString(new ClassPathResource("test-failure.json").getInputStream(),
                Charset.defaultCharset());
        var result = parser.parse(failedData);
        assertNotNull(result);
        assertFalse(result.getOk());
        assertNotNull(result.getMessage());
        assertNull(result.getHouses());
    }

}
