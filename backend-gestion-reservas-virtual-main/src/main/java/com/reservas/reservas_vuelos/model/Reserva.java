package com.reservas.reservas_vuelos.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;

    private Integer idCliente;

    private Integer idVuelo;

    private Integer idPago;
    private Integer telefonoContacto;
    private String correoContacto;
    private LocalDateTime fechaReserva;

    @Column(name = "estado_reserva")
    private String estadoReserva;
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pasajero> pasajeros;
}

