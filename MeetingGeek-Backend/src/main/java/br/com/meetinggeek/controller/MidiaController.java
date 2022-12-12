package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Midia;
import br.com.meetinggeek.repository.MidiaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/midia")
public class MidiaController {

    private final MidiaRepository midiaRepository;

    public MidiaController(MidiaRepository midiaRepository) {
        this.midiaRepository = midiaRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @PostMapping("/salvar")
    @PutMapping
    public ResponseEntity<?> saveMidia(@RequestBody Optional<Midia> midia){
        if (midia.isEmpty()){
            return ResponseEntity.badRequest().body("Mídia não pode estar em branco");
        } else {
            return ResponseEntity.ok(midiaRepository.save(midia.get()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMidia(@PathVariable Long id){
        midiaRepository.deleteById(id);
        return ResponseEntity.ok("Mídia deletada com sucesso");
    }

}
