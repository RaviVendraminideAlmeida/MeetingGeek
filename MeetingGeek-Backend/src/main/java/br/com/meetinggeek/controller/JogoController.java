package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Jogo;
import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.JogoRepository;
import br.com.meetinggeek.repository.UsuarioRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jogos")
@CrossOrigin(origins = "http://localhost:8081")
public class JogoController {

    private final JogoRepository jogoRepository;

    private final UsuarioRepository usuarioRepository;

    public JogoController(JogoRepository jogoRepository, UsuarioRepository usuarioRepository) {
        this.jogoRepository = jogoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @PostMapping("/cadastrar")
    public ResponseEntity <?> saveJogo(@RequestBody Optional<Jogo> jogo){
        if(jogo.isEmpty()){
            return ResponseEntity.badRequest().body("Jogo não pode estar vazio");
        } else {
            return ResponseEntity.ok(jogoRepository.save(jogo.get()));
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/seguir/{idJogo}/{idUsuario}")
    public ResponseEntity<?> followJogo(@PathVariable Long idJogo, @PathVariable Long idUsuario){
        Jogo jogo = jogoRepository.findById(idJogo).get();
        Usuario usuario = usuarioRepository.findById(idUsuario).get();

        List<Jogo> listaJogo = usuario.getJogos();
        listaJogo.add(jogo);

        return ResponseEntity.ok(usuarioRepository.save(usuario));
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/")
    public ResponseEntity<?> findAllJogos (){
        return ResponseEntity.ok(jogoRepository.findAllByAtivo(1));
    }

    @PreAuthorize("hasRole('ROLE_MODERADOR')")
    @DeleteMapping("/desativar/{id}")
    ResponseEntity<?> desativarJogo(@PathVariable Long id){
        Jogo jogo = jogoRepository.findById(id).get();
        jogo.setAtivo(0);
        jogoRepository.save(jogo);
        return ResponseEntity.ok("Jogo desativado com sucesso");
    }


}

