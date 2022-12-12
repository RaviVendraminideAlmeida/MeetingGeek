package br.com.meetinggeek.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Midia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String linkMidia;

    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "comentarioId")
    private Comentario comentario;
}
