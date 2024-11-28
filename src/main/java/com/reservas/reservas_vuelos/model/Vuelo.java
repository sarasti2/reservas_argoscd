package com.reservas.reservas_vuelos.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "vuelo")
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVuelo;

    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private LocalDate fechaLlegada;
    private LocalTime horaLlegada;
}
