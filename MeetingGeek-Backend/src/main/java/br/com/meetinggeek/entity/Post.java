package br.com.meetinggeek.entity;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String conteudo;
    private Integer numeroLikes = 0;
    private LocalDateTime momentoEnvio;

    @ManyToOne
    @JoinColumn(name = "usuarioId")
    private Usuario usuario;

    //@JoinColumn(name = "comentarioId")
    @OneToMany(mappedBy = "post", orphanRemoval = true)
    @JsonManagedReference
    private List<Comentario> comentarios;

    @OneToMany
    @JoinColumn(name = "midiaId")
    private List<Midia> midia;

    public Post(Long id) {
        this.id = id;
    }
}
