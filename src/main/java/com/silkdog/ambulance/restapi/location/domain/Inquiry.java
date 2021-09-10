package com.silkdog.ambulance.restapi.location.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Inquiry {
    private String stage1;
    private String stage2;
    private String pageNo;
    private String numOfRows;
    private Double lng;
    private Double lat;
}
