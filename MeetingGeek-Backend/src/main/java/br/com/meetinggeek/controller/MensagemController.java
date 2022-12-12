package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Chat;
import br.com.meetinggeek.entity.Mensagem;
import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.ChatRepository;
import br.com.meetinggeek.repository.MensagemRepository;
import br.com.meetinggeek.repository.UsuarioRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/mensagens")
@CrossOrigin(origins = "*")
public class MensagemController {

    private final MensagemRepository mensagemRepository;

    private final UsuarioRepository usuarioRepository;

    private final ChatRepository chatRepository;

    public MensagemController(MensagemRepository mensagemRepository, UsuarioRepository usuarioRepository, ChatRepository chatRepository) {
        this.mensagemRepository = mensagemRepository;
        this.usuarioRepository = usuarioRepository;
        this.chatRepository = chatRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @PostMapping("/salvar")
    public ResponseEntity<?> saveMensagem(@RequestBody Optional<Mensagem> mensagem) {
        if (mensagem.isEmpty()) {
            return ResponseEntity.badRequest().body("Dados da mensagem não podem estar em branco");
        } else {
            Usuario usuario = usuarioRepository.findById(mensagem.get().getUsuario().getId()).get();
            mensagem.get().setUsuario(usuario);

            Chat chat = chatRepository.findById(mensagem.get().getChat().getId()).get();
            mensagem.get().setChat(chat);

            return ResponseEntity.ok(mensagemRepository.save(mensagem.get()));
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deleteMensgem(@PathVariable Long id){
        mensagemRepository.deleteById(id);
        return ResponseEntity.ok("Mensagem deletada com sucesso");
    }



}
