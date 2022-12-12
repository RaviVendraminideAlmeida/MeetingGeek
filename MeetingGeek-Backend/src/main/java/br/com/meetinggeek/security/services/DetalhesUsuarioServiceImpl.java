package br.com.meetinggeek.security.services;

import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class DetalhesUsuarioServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByApelido(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário com o apelido: " + username + " não encontrado"));

        return DetalhesUsuarioImpl.build(usuario);
    }


}
