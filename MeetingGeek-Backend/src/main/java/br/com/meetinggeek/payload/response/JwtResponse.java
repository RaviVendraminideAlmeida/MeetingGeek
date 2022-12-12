package br.com.meetinggeek.payload.response;

import br.com.meetinggeek.entity.Jogo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {

    private String nome;

    private String token;
    private Long id;
    private String apelido;
    private String email;
    private List<String> cargos;

    private String type = "Bearer";

    private List<Jogo> jogos;

    public JwtResponse(String nome, String token, Long id, String apelido, String email, List<String> cargos, String type, String bio, String discord, String teamSpeak, String steam, String epic, List<Jogo> jogos) {
        this.nome = nome;
        this.token = token;
        this.id = id;
        this.apelido = apelido;
        this.email = email;
        this.cargos = cargos;
        this.type = type;
        this.bio = bio;
        this.discord = discord;
        this.teamSpeak = teamSpeak;
        this.steam = steam;
        this.epic = epic;
        this.jogos = jogos;
    }

    private String bio;
    private String discord;
    private String teamSpeak;
    private String steam;
    private String epic;


    public JwtResponse(String token, Long id, String apelido, String email, List<String> cargos) {
        this.token = token;
        this.id = id;
        this.apelido = apelido;
        this.email = email;
        this.cargos = cargos;
    }





}
