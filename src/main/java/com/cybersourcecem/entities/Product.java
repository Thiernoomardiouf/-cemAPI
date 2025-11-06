package com.cybersourcecem.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 100)
    private String title;
    private double price;
    @Column(unique = true)
    private String code;
    private boolean promotion = false;
    private boolean selected =  false;
    private boolean available = false;
    @Transient
    private int quantity = 1;
    @ManyToOne
    private Category category;
    @JsonBackReference
    @OneToMany(mappedBy = "product")
    Collection<OrderItem> orderItems = new ArrayList<>();
}
