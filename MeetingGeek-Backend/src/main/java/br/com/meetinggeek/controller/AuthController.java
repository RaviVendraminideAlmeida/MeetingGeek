package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.*;
import br.com.meetinggeek.payload.request.CadastroRequest;
import br.com.meetinggeek.payload.response.JwtResponse;
import br.com.meetinggeek.payload.response.MensagemResponse;
import br.com.meetinggeek.repository.CargoRepository;
import br.com.meetinggeek.repository.UsuarioRepository;
import br.com.meetinggeek.security.services.DetalhesUsuarioImpl;
import br.com.meetinggeek.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import br.com.meetinggeek.payload.request.LoginRequest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class    AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CargoRepository cargoRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUsuario(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getApelido(), loginRequest.getSenha()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        DetalhesUsuarioImpl detalhesUsuario = (DetalhesUsuarioImpl) authentication.getPrincipal();
        List<String> cargos = detalhesUsuario.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Usuario usuario = usuarioRepository.findByApelido(detalhesUsuario.getApelido()).get();

        return ResponseEntity.ok(
                new JwtResponse(
                        usuario.getNome(),
                        jwt,
                        detalhesUsuario.getId(),
                        detalhesUsuario.getApelido(),
                        detalhesUsuario.getEmail(),
                        cargos,
                        "Bearer",
                        usuario.getBio(),
                        usuario.getDiscord(),
                        usuario.getTeamSpeak(),
                        usuario.getSteam(),
                        usuario.getEpic(),
                        usuario.getJogos()
                )
        );
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<?> registerUsuario(@RequestBody CadastroRequest cadastroRequest) {
        if (usuarioRepository.existsByApelido(cadastroRequest.getApelido())) {
            return ResponseEntity.badRequest().body(new MensagemResponse("Erro: Apelido já está sendo utilizado!"));
        }

        if (usuarioRepository.existsByEmail(cadastroRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MensagemResponse("Erro: Email já está sendo utilizado"));
        }

        Usuario usuario = new Usuario
                (cadastroRequest.getNome(),
                cadastroRequest.getApelido(),
                cadastroRequest.getEmail(),
                encoder.encode(cadastroRequest.getSenha()),
                cadastroRequest.getBio(),
                cadastroRequest.getDiscord(),
                cadastroRequest.getTeamSpeak(),
                cadastroRequest.getSteam(),
                cadastroRequest.getEpic(),
                new ArrayList<Post>(),
                new ArrayList<Denuncia>(),
                new ArrayList<Mensagem>(),
                new ArrayList<Jogo>());

        Set<String> strCargos = cadastroRequest.getCargos();
        System.out.println(strCargos);
        Set<Cargo> cargos = new HashSet<>();

        if(strCargos == null){
            Cargo cargoUsuario = cargoRepository.findByNome(ECargo.ROLE_USUARIO)//
                    .orElseThrow(() -> new RuntimeException("Erro: Cargo não encontrado"));
            cargos.add(cargoUsuario);
        } else {
            strCargos.forEach(cargo -> {
                switch (cargo){
                    case "mod":
                        Cargo cargoModerador = cargoRepository.findByNome(ECargo.ROLE_MODERADOR)//
                                .orElseThrow(() -> new RuntimeException("Erro: Cargo não encontrado"));
                        cargos.add(cargoModerador);
                        break;
                    default:
                        Cargo cargoUsuario = cargoRepository.findByNome(ECargo.ROLE_USUARIO)//
                                .orElseThrow(() -> new RuntimeException("Erro: cargo não encontrado"));
                        cargos.add(cargoUsuario);
                }
            });
        }



        usuario.setCargos(cargos);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new MensagemResponse("Usuário registrado com sucesso"));
    }

}
