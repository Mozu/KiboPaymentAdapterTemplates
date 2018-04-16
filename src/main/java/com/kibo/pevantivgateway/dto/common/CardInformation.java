package com.kibo.pevantivgateway.dto.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

public class CardInformation implements Serializable {
    @Getter
    @Setter
    public String id;
    @Getter
    @Setter
    public String numberPart;
    @Getter
    @Setter
    public String cvv;
    @Getter
    @Setter
    public Integer expireYear;
    @Getter
    @Setter
    public Integer expireMonth;
    @Getter
    @Setter
    public String type;
    @Getter
    @Setter
    public String cardHolderName;
    @Getter
    @Setter
    public Integer cardIssueMonth;
    @Getter
    @Setter
    public Integer cardIssueYear;
    @Getter
    @Setter
    public String cardIssueNumber;
    @Getter
    @Setter
    public Boolean persistCard;
}
