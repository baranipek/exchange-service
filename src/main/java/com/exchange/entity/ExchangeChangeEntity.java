package com.exchange.entity;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "exchange_rates")
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeChangeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    private String base;

    private String symbol;

    private BigDecimal rate;

    private Date createdTime;

}
