package br.com.meetinggeek.repository;

import br.com.meetinggeek.entity.Cargo;
import br.com.meetinggeek.entity.ECargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    Optional<Cargo> findByNome(ECargo nome);
}
