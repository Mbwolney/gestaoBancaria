package com.gestaoBancaria.api.conta.repository;

import com.gestaoBancaria.api.conta.entity.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroConta(Long numeroConta);
    boolean existsByNumeroConta(Long numeroConta);
}
