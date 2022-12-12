package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Cargo;
import br.com.meetinggeek.entity.Denuncia;
import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.DenunciaRepository;
import br.com.meetinggeek.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/denuncias")
@CrossOrigin(origins = "*")
public class DenunciaController {

    private final DenunciaRepository denunciaRepository;

    private final UsuarioRepository usuarioRepository;

    public DenunciaController(DenunciaRepository denunciaRepository, UsuarioRepository usuarioRepository) {
        this.denunciaRepository = denunciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @PostMapping("/salvar")
    public ResponseEntity<?> saveDenuncia(@RequestBody Optional<Denuncia> denuncia){
        if(denuncia.isEmpty()){
            return ResponseEntity.badRequest().body("Os dados da denúncia não podem estar em branco");
        } else {
            Denuncia denunciaFinal = denuncia.get();
            denunciaFinal.setUsuario(usuarioRepository.findById(denunciaFinal.getUsuario().getId()).get());
            return ResponseEntity.ok(
                    denunciaRepository.save(denunciaFinal));
        }
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @GetMapping("/")
    public ResponseEntity<Iterable<Denuncia>> findAllDenuncias(){
        return ResponseEntity.ok(denunciaRepository.findAll());
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @GetMapping("/aceitar/{id}")
    public ResponseEntity<?> aceitarDenuncia(@PathVariable Long id){
        Denuncia denuncia = denunciaRepository.findById(id).get();
        Usuario usuario = usuarioRepository.findByApelido(denuncia.getUsuario().getApelido()).get();
        usuario.setCargos(null);
        usuarioRepository.save(usuario);

        denunciaRepository.deleteById(id);

        return ResponseEntity.ok("Usuário banido com sucesso");
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deleteDenuncia(@PathVariable Long id){
        denunciaRepository.deleteById(id);
        return ResponseEntity.ok("Denúncia deletada com sucesso");
    }
}
