package com.kibo.pevantivgateway.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Address implements Serializable {
    @Getter
    @Setter
    String line1;
    @Getter
    @Setter
    String line2;
    @Getter
    @Setter
    String line3;
    @Getter
    @Setter
    String state;
    @Getter
    @Setter
    String postalCode;
    @Getter
    @Setter
    String city;
    @Getter
    @Setter
    String country;
    @Getter
    @Setter
    String companyName;
}
