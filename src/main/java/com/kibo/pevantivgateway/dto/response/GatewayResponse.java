package com.kibo.pevantivgateway.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class GatewayResponse implements Serializable {
    public static enum ConnectionStatuses {
        Success,
        Timeout,
        Reject,
        Unauth,
        Error,
        NotFound;
    }

    @Getter
    @Setter
    ConnectionStatuses remoteConnectionStatus;
    @Getter
    @Setter
    String responseText;
    @Getter
    @Setter
    String responseCode;
    @Getter
    @Setter
    Boolean isDeclined;
    @Getter
    @Setter
    Map<String, String> responseData = new HashMap<>();
}
