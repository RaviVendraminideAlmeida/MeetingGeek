package br.com.meetinggeek.repository;

import br.com.meetinggeek.entity.Midia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MidiaRepository extends JpaRepository<Midia, Long> {
}
