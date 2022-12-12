package br.com.meetinggeek.repository;

import br.com.meetinggeek.entity.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JogoRepository extends JpaRepository<Jogo, Long> {
    Optional<Jogo> findByNome(String nome);
    Optional<List<Jogo>> findAllByAtivo(int status);

}
