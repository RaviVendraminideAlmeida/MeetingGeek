package br.com.meetinggeek.repository;

import br.com.meetinggeek.entity.Comentario;
import br.com.meetinggeek.entity.Post;
import br.com.meetinggeek.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByConteudo (String conteudo);
    Iterable<Post> findAllByUsuarioIsOrderByMomentoEnvioDesc (Usuario usuario);
    Optional<Post> findPostById(Long id);

    List<Post> findAllByUsuario(Usuario usuario);



}
