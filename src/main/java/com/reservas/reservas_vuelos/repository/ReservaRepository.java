package com.reservas.reservas_vuelos.repository;

import com.reservas.reservas_vuelos.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Nuevo método para buscar por id_cliente
    List<Reserva> findByIdCliente(Integer idCliente);
}
