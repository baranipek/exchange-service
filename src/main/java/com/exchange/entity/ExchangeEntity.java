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
public class ExchangeEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)

    @Id
    @Setter(AccessLevel.PROTECTED)
    private Long id;

    @Column(name = "base")
    private String base;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "rate", precision = 19, scale = 4)
    private BigDecimal rate;

    @Column(name = "created_time")
    private Date createdTime;

}
