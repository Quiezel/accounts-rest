/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.quiezel.accountsrest.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 *
 * @author Chalio
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Movimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private BigDecimal monto;
    @NonNull
    private LocalDate fecha;
    @NonNull
    private LocalTime hora;
    @NonNull
    private String quien;
    @NonNull
    private String concepto;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY,
            targetEntity = Cuenta.class)
    @JoinColumn(name = "cuentaId")
    private Cuenta cuenta;


}
