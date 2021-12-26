package com.firma.psp.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDataDTO {

    private Long merchantOrderId;

    private Timestamp merchantTimestamp;

    private Double amount;

    private List<PaymentAttributeDTO> attributes;
    
    private String successURL;

    private String errorURL;

    private String failedURL;
}
