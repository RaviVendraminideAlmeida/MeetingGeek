package br.com.meetinggeek.payload.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CadastroRequest {

    @NotNull
    private String apelido;
    @NotNull
    private String nome;
    @NotNull
    private String senha;
    @NotNull
    private String email;
    private String bio;
    private String discord;
    private String teamSpeak;
    private String steam;
    private String epic;
    private Set<String> cargos;

}
