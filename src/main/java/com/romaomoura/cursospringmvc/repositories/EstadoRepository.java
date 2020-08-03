package com.romaomoura.cursospringmvc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.romaomoura.cursospringmvc.domain.locale.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

    @Transactional(readOnly = true)
    public List<Estado> findAllByOrderByNome();

}
