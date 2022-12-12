package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Post;
import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.PostRepository;
import br.com.meetinggeek.repository.UsuarioRepository;
import org.apache.coyote.Response;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/posts")
public class PostController {

    private final UsuarioRepository usuarioRepository;
    private final PostRepository postRepository;

    public PostController(UsuarioRepository usuarioRepository, PostRepository postRepository) {
        this.usuarioRepository = usuarioRepository;
        this.postRepository = postRepository;
    }



    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @PostMapping("/salvar")
    public ResponseEntity<?> savePost (@RequestBody Optional<Post> post){
        if(post.isEmpty() || usuarioRepository.findById(post.get().getUsuario().getId()).isEmpty()){
            return ResponseEntity.badRequest().body("Post não pode estar em braco");
        }else{
            Post postFinal = post.get();
            postFinal.setMomentoEnvio(LocalDateTime.now());
            postFinal.setUsuario(usuarioRepository.findById(postFinal.getUsuario().getId()).get());
            return ResponseEntity.ok(
                    postRepository.save(
                            post.get()

                    ));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @PutMapping("/alterar/{idPost}")
    public ResponseEntity<?> updatePost(@RequestBody Optional<Post> post, @PathVariable Long idPost){
        Post postExistente = postRepository.findPostById(idPost).get();
        postExistente.setConteudo(post.get().getConteudo());
        return ResponseEntity.ok(postRepository.save(postExistente));
    }


    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/procurar/{idUsuario}")
    public ResponseEntity<?> findAllByUsuario(@PathVariable Long idUsuario){
        return ResponseEntity.ok(postRepository.findAllByUsuario(new Usuario(idUsuario)));
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/carregar/{id}")
    public ResponseEntity<?> findPost(@PathVariable Long id){
        return ResponseEntity.ok(postRepository.findById(id));
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        postRepository.deleteById(id);

        return ResponseEntity.ok("Post excluído com sucesso");
    }

/*    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/procurar/")
    public ResponseEntity<?> findAllByUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(postRepository.findAllByUsuarioIsOrderByMomentoEnvioDesc(usuario));
    }*/

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/")
    public ResponseEntity<?> findAllPosts(){
        return ResponseEntity.ok(postRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/curtir/{idPost}")
    public void likePost(@PathVariable Long idPost){
        Post post = postRepository.findPostById(idPost).get();
        post.setNumeroLikes(post.getNumeroLikes() + 1);
        postRepository.save(post);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/descurtir/{idPost}")
    public void unlikePost(@PathVariable Long idPost){
        Post post = postRepository.findPostById(idPost).get();
        post.setNumeroLikes(post.getNumeroLikes() - 1);
        postRepository.save(post);

    }
}
