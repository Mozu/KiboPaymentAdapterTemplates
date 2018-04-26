package com.kibo.pevantivgateway.dto.common;

public enum TransactionType {
    Sale,
    Authorize,
    Capture,
    Force,
    Credit,
    Void,
    AuthorizeAndCapture,
    Information,
    Init3dSecure,
    Finalize3dSecure,
    FraudScreen,
    Debit;
}
