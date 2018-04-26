package com.kibo.pevantivgateway.dto.response;

import com.kibo.pevantivgateway.dto.common.KeyValueTuple;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    List<KeyValueTuple> responseData = new ArrayList<>();
}
