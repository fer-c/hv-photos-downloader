package com.homevision.photosdownloader.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class House {
    @JsonProperty
    private Long id;
    @JsonProperty
    private String address;
    @JsonProperty("homeowner")
    private String owner;
    @JsonProperty
    private Double price;
    @JsonProperty
    private String photoURL;
}
