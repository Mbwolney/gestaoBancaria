package com.gestaoBancaria.api.transacao.repository;

import com.gestaoBancaria.api.transacao.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
    List<Transacao> findByContaNumeroConta(Long numeroConta);
}
