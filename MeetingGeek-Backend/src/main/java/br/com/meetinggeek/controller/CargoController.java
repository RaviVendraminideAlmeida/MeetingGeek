package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Cargo;
import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.CargoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    private final CargoRepository cargoRepository;

    public CargoController(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Cargo>> getAllCargos(){
        return ResponseEntity.ok(cargoRepository.findAll());
    }

    @GetMapping("/carregar/{id}")
    public ResponseEntity<Cargo> findById(@PathVariable Long id) { return ResponseEntity.ok(cargoRepository.findById(id).get());}


}
