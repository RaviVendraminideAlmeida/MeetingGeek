package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Comentario;
import br.com.meetinggeek.entity.Post;
import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.ComentarioRepository;
import br.com.meetinggeek.repository.PostRepository;
import br.com.meetinggeek.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/comentarios")
public class ComentarioController {

    private final UsuarioRepository usuarioRepository;

    private final ComentarioRepository comentarioRepository;

    private final PostRepository postRepository;

    public ComentarioController(UsuarioRepository usuarioRepository, ComentarioRepository comentarioRepository, PostRepository postRepository) {
        this.usuarioRepository = usuarioRepository;
        this.comentarioRepository = comentarioRepository;
        this.postRepository = postRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @PostMapping("/salvar")
    public ResponseEntity<?> saveComentario(@RequestBody Optional<Comentario> comentario){
        if(comentario.isEmpty()){
            return ResponseEntity.badRequest().body("Os dados do comentário não podem estar em branco");
        } else {
            Post post = postRepository.findPostById(comentario.get().getPost().getId()).get();
            comentario.get().setPost(post);
            Usuario usuario = usuarioRepository.findById(comentario.get().getUsuario().getId()).get();
            comentario.get().setUsuario(usuario);

            comentario.get().setMomentoEnvio(LocalDateTime.now());

            return ResponseEntity.ok(comentarioRepository.save(comentario.get()));
        }
    }

    @GetMapping("/{idPost}")
    public ResponseEntity<?> getComentariosByPost(@PathVariable Long idPost){
        return ResponseEntity.ok(comentarioRepository.findAllByPost(new Post(idPost)));
    }

    /*@PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<Comentario> getComentarioById(@PathVariable Long id){
        return ResponseEntity.ok(comentarioRepository.findById(id).get());
    }*/

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deleteComentario(@PathVariable Long id){
        comentarioRepository.deleteById(id);
        return ResponseEntity.ok("Comentario deletado com sucesso");
    }
}

