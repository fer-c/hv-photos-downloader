package com.homevision.photosdownloader.core;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class HousesData {
    public static final HousesData FAILED_DATA = HousesData.builder()
            .ok(false)
            .message("Failed to retrieve")
            .houses(Collections.emptyList())
            .build();
    @JsonProperty
    private Boolean ok;
    @JsonProperty
    private String message;
    @JsonProperty
    private List<House> houses;
}
