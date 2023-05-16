package com.homevision.photosdownloader.io;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.homevision.photosdownloader.core.HousesData;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class HousesDataParserImpl implements HousesDataParser{

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public HousesData parse(final String text) {
        try {
            return mapper.readValue(text, HousesData.class);
        } catch (JsonProcessingException e) {
            log.error("Falsed to parse {}", text, e);
        }
        return HousesData.builder().ok(false).build();
    }
}
