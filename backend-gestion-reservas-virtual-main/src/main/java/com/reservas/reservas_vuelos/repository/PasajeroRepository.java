package com.reservas.reservas_vuelos.repository;

import com.reservas.reservas_vuelos.model.Pasajero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasajeroRepository extends JpaRepository<Pasajero, Long> {
}
