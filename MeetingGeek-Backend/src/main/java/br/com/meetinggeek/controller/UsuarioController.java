package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Post;
import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        this.usuarioRepository = usuarioRepository;
        this.encoder = encoder;
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Usuario>> getAllUsuarios() {return ResponseEntity.ok(usuarioRepository.findAll());}

    @GetMapping("/carregar/{apelido}")
    public ResponseEntity<Usuario> findByApelido(@PathVariable String apelido) { return ResponseEntity.ok(usuarioRepository.findByApelido(apelido).get()); }

    @GetMapping("/procurar")
    public ResponseEntity<?> findByPosts(@RequestBody Post post){
        Optional<Usuario> usuario = usuarioRepository.findAllByPostsContaining(post);
        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }else {
            return ResponseEntity.badRequest().body("Busca inválida");
        }

    }

    @PostMapping("/salvar")
    @PutMapping
    public ResponseEntity<?> saveUsuario(@RequestBody Optional<Usuario> usuario) {
        if (usuario.isEmpty()) {
            return ResponseEntity.badRequest().body("Os dados do usuário não podem estar em branco");
        } else {
            usuario.get().setSenha(encoder.encode(usuario.get().getSenha()));
            return ResponseEntity.ok(usuarioRepository.save(usuario.get())); }
    }

    @GetMapping("/validarSenha")
    public ResponseEntity<Boolean> validarSenha(@RequestParam String apelido, @RequestParam String senha) {
        Optional<Usuario> optUsuario = usuarioRepository.findByApelido(apelido);
        if(optUsuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }
        boolean valido = encoder.matches(senha, optUsuario.get().getSenha());
        HttpStatus status = (valido) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valido);
    }

}

