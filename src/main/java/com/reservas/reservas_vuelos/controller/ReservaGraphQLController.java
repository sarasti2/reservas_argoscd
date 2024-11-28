package com.reservas.reservas_vuelos.controller;

import com.reservas.reservas_vuelos.model.Pasajero;
import com.reservas.reservas_vuelos.model.Reserva;
import com.reservas.reservas_vuelos.model.Vuelo;
import com.reservas.reservas_vuelos.repository.PasajeroRepository;
import org.springframework.transaction.annotation.Transactional;
import com.reservas.reservas_vuelos.model.input.PasajeroInput;
import com.reservas.reservas_vuelos.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class ReservaGraphQLController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private PasajeroRepository pasajeroRepository;

    @MutationMapping
    @Transactional
    public Reserva crearReserva(
            @Argument Integer idCliente,
            @Argument Integer idVuelo,
            @Argument Integer idPago,
            @Argument Integer telefonoContacto,
            @Argument String correoContacto,
            @Argument String estadoReserva,
            @Argument List<PasajeroInput> pasajeros
    ) {
        // Crear la nueva reserva
        Reserva reserva = new Reserva();
        reserva.setIdCliente(idCliente);
        reserva.setIdVuelo(idVuelo);
        reserva.setIdPago(idPago);
        reserva.setTelefonoContacto(telefonoContacto);
        reserva.setCorreoContacto(correoContacto);
        reserva.setEstadoReserva(estadoReserva);
        reserva.setFechaReserva(LocalDateTime.now());

        // Asociar los pasajeros a la reserva
        List<Pasajero> listaPasajeros = pasajeros.stream().map(p -> {
            Pasajero pasajero = new Pasajero();
            pasajero.setNombrePasajero(p.getNombrePasajero());
            pasajero.setApellidoPasajero(p.getApellidoPasajero());
            pasajero.setTipoDocumento(p.getTipoDocumento());
            pasajero.setNumeroDocumento(p.getNumeroDocumento());
            pasajero.setTelefonoContacto(p.getTelefonoContacto());
            pasajero.setCorreoContacto(p.getCorreoContacto());
            pasajero.setReserva(reserva); // Asociar la reserva a cada pasajero
            return pasajero;
        }).collect(Collectors.toList());

        // Guardar la reserva con los pasajeros
        return reservaService.crearReserva(reserva, listaPasajeros);
    }

    @MutationMapping
    @Transactional
    public Reserva editarContactoReserva(
            @Argument Long idReserva,
            @Argument Integer telefonoContacto,
            @Argument String correoContacto
    ) {
        // Buscar la reserva
        Reserva reserva = reservaService.consultarReserva(idReserva)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + idReserva));

        // Actualizar los datos de contacto
        reserva.setTelefonoContacto(telefonoContacto);
        reserva.setCorreoContacto(correoContacto);

        // Guardar los cambios
        return reservaService.guardarReserva(reserva);
    }

    @MutationMapping
    @Transactional
    public Reserva editarPasajeros(
            @Argument Long idReserva,
            @Argument List<Pasajero> pasajeros
    ) {
        Optional<Reserva> reservaOpt = reservaService.consultarReserva(idReserva);

        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();

            pasajeros.forEach(input -> {
                Optional<Pasajero> pasajeroOpt = pasajeroRepository.findById(input.getIdPasajero());

                if (pasajeroOpt.isPresent()) {
                    Pasajero pasajero = pasajeroOpt.get();
                    if (input.getNombrePasajero() != null) pasajero.setNombrePasajero(input.getNombrePasajero());
                    if (input.getApellidoPasajero() != null) pasajero.setApellidoPasajero(input.getApellidoPasajero());
                    if (input.getTipoDocumento() != null) pasajero.setTipoDocumento(input.getTipoDocumento());
                    if (input.getNumeroDocumento() != null) pasajero.setNumeroDocumento(input.getNumeroDocumento());

                    pasajeroRepository.save(pasajero);
                } else {
                    throw new RuntimeException("Pasajero no encontrado con ID: " + input.getIdPasajero());
                }
            });

            return reserva;
        } else {
            throw new RuntimeException("Reserva no encontrada con ID: " + idReserva);
        }
    }

    @QueryMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    @QueryMapping
    public Optional<Reserva> consultarReserva(@Argument("id") Long id) {
        return reservaService.consultarReserva(id);
    }

    @QueryMapping
    public List<Reserva> consultarReservasPorCliente(@Argument("idCliente") Integer idCliente) {
        return reservaService.consultarReservasPorCliente(idCliente);
    }

    @MutationMapping
    public Reserva cancelarReserva(@Argument("idReserva") Long idReserva) {
        return reservaService.cancelarReserva(idReserva);
    }
}

