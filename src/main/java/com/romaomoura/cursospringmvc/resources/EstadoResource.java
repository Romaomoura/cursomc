package com.romaomoura.cursospringmvc.resources;

import java.util.List;
import java.util.stream.Collectors;

import com.romaomoura.cursospringmvc.domain.locale.Cidade;
import com.romaomoura.cursospringmvc.domain.locale.Estado;
import com.romaomoura.cursospringmvc.dto.CidadeDto;
import com.romaomoura.cursospringmvc.dto.EstadoDto;
import com.romaomoura.cursospringmvc.services.CidadeService;
import com.romaomoura.cursospringmvc.services.EstadoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estados")
public class EstadoResource {

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<EstadoDto>> findAll() {
        List<Estado> estados = estadoService.findAll();
        List<EstadoDto> estadosDto = estados.stream().map(obj -> new EstadoDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(estadosDto);
    }

    @RequestMapping(value = "/{estadoId}/cidades", method = RequestMethod.GET)
    public ResponseEntity<List<CidadeDto>> findCidade(@PathVariable Integer estadoId) {
        List<Cidade> cidades = cidadeService.findByEstado(estadoId);
        List<CidadeDto> cidadesDto = cidades.stream().map(obj -> new CidadeDto(obj)).collect(Collectors.toList());
        return ResponseEntity.ok().body(cidadesDto);
    }

}