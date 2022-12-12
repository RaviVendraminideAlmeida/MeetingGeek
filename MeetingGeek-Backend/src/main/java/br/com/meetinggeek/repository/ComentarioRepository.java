package br.com.meetinggeek.repository;

import br.com.meetinggeek.entity.Comentario;
import br.com.meetinggeek.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    Optional<List<Comentario>> findAllByPost(Post post);
}
