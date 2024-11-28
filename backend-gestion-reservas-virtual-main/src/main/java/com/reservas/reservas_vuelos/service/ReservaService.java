package com.reservas.reservas_vuelos.service;

import com.reservas.reservas_vuelos.model.Pasajero;
import com.reservas.reservas_vuelos.model.Reserva;
import com.reservas.reservas_vuelos.repository.PasajeroRepository;
import com.reservas.reservas_vuelos.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private PasajeroRepository pasajeroRepository;

    @Transactional
    public Reserva crearReserva(Reserva reserva, List<Pasajero> pasajeros) {
        Reserva reservaGuardada = reservaRepository.save(reserva);
        pasajeros.forEach(p -> p.setReserva(reservaGuardada));
        pasajeroRepository.saveAll(pasajeros);

        // Asignar los pasajeros guardados a la reserva
        reservaGuardada.setPasajeros(pasajeros);
        return reservaGuardada;
    }

    public Optional<Reserva> consultarReserva(Long id) {
        return reservaRepository.findById(id);
    }

    public Reserva guardarReserva(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public List<Reserva> consultarReservasPorCliente(Integer idCliente) {
        return reservaRepository.findByIdCliente(idCliente);
    }

    public Reserva cancelarReserva(Long idReserva) {
        Optional<Reserva> reservaOpt = reservaRepository.findById(idReserva);
        if (reservaOpt.isPresent()) {
            Reserva reserva = reservaOpt.get();
            reserva.setEstadoReserva("cancelada");
            return reservaRepository.save(reserva);
        } else {
            throw new RuntimeException("Reserva no encontrada con ID: " + idReserva);
        }
    }
}

