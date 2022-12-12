package br.com.meetinggeek.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import br.com.meetinggeek.entity.Post;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "apelido"),
                @UniqueConstraint(columnNames = "email")
        }
)
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(unique = true, nullable = false)
    private String apelido;
    @Column(unique = true, nullable = false)
    private String email;
    private String senha;
    @Column(length = 512)
    private String bio;
    private String discord;
    private String teamSpeak;
    private String steam;
    private String epic;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario")
    private List<Denuncia> denuncias;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Mensagem> mensagens;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Comentario> comentarios;

    @ManyToMany(cascade = CascadeType.ALL)
            @JoinTable(name = "usuario_jogo",
            joinColumns = @JoinColumn(name = "jogo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id",
                    referencedColumnName = "id"))
    private List<Jogo> jogos;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="usuario_cargos",
               joinColumns = @JoinColumn(name="usuario_id"),
               inverseJoinColumns = @JoinColumn(name="cargo_id"))
    private Set<Cargo> cargos = new HashSet<>();


    public Usuario(String nome, String apelido, String email, String senha, String bio, String discord, String teamSpeak, String steam, String epic, List<Post> posts, List<Denuncia> denuncias, List<Mensagem> mensagens, List<Jogo> jogos) {
        this.nome = nome;
        this.apelido = apelido;
        this.email = email;
        this.senha = senha;
        this.bio = bio;
        this.discord = discord;
        this.teamSpeak = teamSpeak;
        this.steam = steam;
        this.epic = epic;
        this.posts = posts;
        this.denuncias = denuncias;
        this.mensagens = mensagens;
        this.jogos = jogos;
    }

    public Usuario(Long id) {
        this.id = id;
    }
}
