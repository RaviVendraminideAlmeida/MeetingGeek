package br.com.meetinggeek.payload.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotNull
    private String apelido;

    @NotNull
    private String senha;

}
