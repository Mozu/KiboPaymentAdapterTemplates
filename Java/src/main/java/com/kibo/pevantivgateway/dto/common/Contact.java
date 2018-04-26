package com.kibo.pevantivgateway.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class Contact implements Serializable {
    @Getter
    @Setter
    String firstname;
    @Getter
    @Setter
    String lastname;
    @Getter
    @Setter
    String email;
    @Getter
    @Setter
    String phone;
    @Getter
    @Setter
    String country;
}
