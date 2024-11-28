package com.reservas.reservas_vuelos.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pasajero")
public class Pasajero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPasajero;

    private String nombrePasajero;
    private String apellidoPasajero;
    private String tipoDocumento;
    private String numeroDocumento;
    private String telefonoContacto;
    private String correoContacto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;
}
