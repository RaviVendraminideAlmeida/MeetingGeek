package br.com.meetinggeek.repository;

import br.com.meetinggeek.entity.Chat;
import br.com.meetinggeek.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<List<Chat>> findAllByNome (String nome);

    Optional<List<Chat>> findAllByUsuariosContaining (Usuario usuario);

    Optional<List<Chat>> findAllByUsuariosIn (List<Usuario> usuarios);

    Optional<List<Chat>> findAllByUsuariosIsIn (List<Usuario> usuarios);

}
