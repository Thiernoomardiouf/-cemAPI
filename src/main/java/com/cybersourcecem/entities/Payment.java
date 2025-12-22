package com.cybersourcecem.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Payment {
    //public class Payment implements Serializable {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    //private Date date;
    //private String cardType;
    //private String cardNumber;
    //@OneToOne(mappedBy = "payment")
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //private Order order;

    private Long orderId;

    private String cybersourceRequestId;

    private String status;  // AUTHORIZED, CAPTURED, DECLINED

    @Column(columnDefinition = "TEXT")
    private String rawResponse;

    private LocalDateTime createdAt = LocalDateTime.now();
}
