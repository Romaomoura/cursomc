package com.romaomoura.cursospringmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.romaomoura.cursospringmvc.domain.locale.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
