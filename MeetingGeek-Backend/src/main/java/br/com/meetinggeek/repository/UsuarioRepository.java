package br.com.meetinggeek.repository;

import br.com.meetinggeek.entity.Jogo;
import br.com.meetinggeek.entity.Post;
import br.com.meetinggeek.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByApelido(String apelido);
    Boolean existsByApelido(String apelido);
    Boolean existsByEmail(String email);

    Optional<Usuario> findAllByPostsContaining(Post post);

    Optional<List<Usuario>> findAllByJogosContaining(Jogo jogo);

}
