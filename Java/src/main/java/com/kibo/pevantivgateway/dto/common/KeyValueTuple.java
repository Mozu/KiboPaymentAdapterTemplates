package com.kibo.pevantivgateway.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class KeyValueTuple implements Serializable {
    @Getter
    @Setter
    String key;
    @Getter
    @Setter
    Object value;

    public KeyValueTuple() {}

    public KeyValueTuple(String key, Object value) { this.key = key; this.value = value; }
}
