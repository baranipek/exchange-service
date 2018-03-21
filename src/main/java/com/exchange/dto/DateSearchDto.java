package com.exchange.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class DateSearchDto implements Serializable {

    private static final long serialVersionUID = 7268063789808550714L;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date startDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    Date endDate;

}
