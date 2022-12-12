package br.com.meetinggeek.security.services;

import br.com.meetinggeek.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class DetalhesUsuarioImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String apelido;

    private String email;

    @JsonIgnore
    private String senha;

    private Collection<? extends GrantedAuthority> autorizacoes;

    public static DetalhesUsuarioImpl build(Usuario usuario) {
        List<GrantedAuthority> autorizacoes = usuario.getCargos().stream()
                .map(cargo -> new SimpleGrantedAuthority(cargo.getNome().name()))
                .collect(Collectors.toList());

        return new DetalhesUsuarioImpl(
                usuario.getId(),
                usuario.getApelido(),
                usuario.getEmail(),
                usuario.getSenha(),
                autorizacoes
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autorizacoes;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return apelido;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DetalhesUsuarioImpl user = (DetalhesUsuarioImpl) o;
        return Objects.equals(id, user.id);
    }
}
