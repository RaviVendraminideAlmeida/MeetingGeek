package br.com.meetinggeek.controller;

import br.com.meetinggeek.entity.Chat;
import br.com.meetinggeek.entity.Jogo;
import br.com.meetinggeek.entity.Usuario;
import br.com.meetinggeek.repository.ChatRepository;
import br.com.meetinggeek.repository.JogoRepository;
import br.com.meetinggeek.repository.UsuarioRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "*")
public class ChatController {

    private final ChatRepository chatRepository;

    private final UsuarioRepository usuarioRepository;

    private final JogoRepository jogoRepository;

    public ChatController(ChatRepository chatRepository, UsuarioRepository usuarioRepository, JogoRepository jogoRepository) {
        this.chatRepository = chatRepository;
        this.usuarioRepository = usuarioRepository;
        this.jogoRepository = jogoRepository;
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/")
    public ResponseEntity<Iterable<Chat>> findAllChats() {
        return ResponseEntity.ok(chatRepository.findAll());
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/carregar/{id}")
    public ResponseEntity<?> getChatById(@PathVariable Long id) {
        Optional<Chat> chat = chatRepository.findById(id);
        if (chat.isEmpty()) {
            return ResponseEntity.badRequest().body("Chat não encontrado");
        } else {
            return ResponseEntity.ok().body(chat);
        }
    }

    @GetMapping("/procurar/{idUsuario}")
    public ResponseEntity<?> getChatsByUsuario(@PathVariable Long idUsuario) {
        //List<Usuario> listaUsuario = new ArrayList<>();
        //listaUsuario.add();
        Iterable<Chat> chats = chatRepository.findAllByUsuariosContaining(new Usuario(idUsuario)).get();
        return ResponseEntity.ok(chats);
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @PostMapping("/salvar")
    @PutMapping
    public ResponseEntity<?> saveChat(@RequestBody Optional<Chat> chat) {
        if (chat.isEmpty()) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        } else {
            Usuario usuario1 = usuarioRepository.findById(chat.get().getUsuarios().get(0).getId()).get();
            Usuario usuario2 = usuarioRepository.findById(chat.get().getUsuarios().get(1).getId()).get();

            List<Usuario> listaUsuarios = new ArrayList<>();
            listaUsuarios.add(usuario1);
            listaUsuarios.add(usuario2);

            if (chatRepository.findAllByNome(
                            listaUsuarios.get(0).getApelido() + " e " + listaUsuarios.get(1).getApelido()).get()
                    .toArray().length == 0 &&
                    chatRepository.findAllByNome(
                                    listaUsuarios.get(1).getApelido() + " e " + listaUsuarios.get(0).getApelido()).get()
                            .toArray().length == 0) {

                chat.get().setUsuarios(listaUsuarios);
                chat.get().setNome(usuario1.getApelido() + " e " + usuario2.getApelido());

                return ResponseEntity.ok(chatRepository.save(chat.get()));
            } else {
                return ResponseEntity.badRequest().body("Chat já existe");
            }


        }
    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deleteChat(@PathVariable Long id) {
        chatRepository.deleteById(id);
        return ResponseEntity.ok("Chat deletado com Sucesso");

    }

    @PreAuthorize("hasAnyRole('ROLE_MODERADOR','ROLE_USUARIO')")
    @GetMapping("/match/{idUsuario}/{idJogo}")
    public ResponseEntity<?> matchUsuario(@PathVariable Long idUsuario, @PathVariable Long idJogo) {
        Jogo jogo = jogoRepository.findById(idJogo).get();
        List<Usuario> listaUsuariosEncontrados = usuarioRepository.findAllByJogosContaining(jogo).get();

        Usuario usuario1 = usuarioRepository.findById(idUsuario).get();
        listaUsuariosEncontrados.removeIf(usuario -> usuario.equals(usuario1));

        if (listaUsuariosEncontrados.toArray().length == 0) {
            return ResponseEntity.badRequest().body("Nenhum usuário encontrado");
        } else {

            List<Usuario> listaUsuarios = new ArrayList<>();
            listaUsuarios.add(usuario1);

            for (int i = 0; i < listaUsuariosEncontrados.size(); i++) {
                Usuario usuario2 = listaUsuariosEncontrados.get(i);
                listaUsuarios.add(usuario2);

                System.out.println(usuario2.getNome());


                if (chatRepository.findAllByNome(
                        listaUsuarios.get(0).getApelido() + " e " + listaUsuarios.get(1).getApelido()).get()
                            .toArray().length == 0 &&
                        chatRepository.findAllByNome(
                        listaUsuarios.get(1).getApelido() + " e " + listaUsuarios.get(0).getApelido()).get()
                            .toArray().length == 0) {
                    break;
                } else {
                    listaUsuarios.remove(listaUsuarios.size() - 1);
                }
            }

            if (chatRepository.findAllByNome(
                    listaUsuarios.get(0).getApelido() + " e " + listaUsuarios.get(1).getApelido()).get()
                    .toArray().length == 0 &&
                    chatRepository.findAllByNome(
                    listaUsuarios.get(1).getApelido() + " e " + listaUsuarios.get(0).getApelido()).get()
                    .toArray().length == 0) {
                Chat chat = new Chat();
                chat.setNome(listaUsuarios.get(0).getApelido() + " e " + listaUsuarios.get(1).getApelido());
                chat.setUsuarios(listaUsuarios);

                return ResponseEntity.ok(chatRepository.save(chat));
            } else {
                return ResponseEntity.badRequest().body("Chat já existe ou não existem usuários suficientes");
            }

        }

    }


    @GetMapping("/teste/{idUsuario1}/{idUsuario2}")
    public ResponseEntity<?> teste(@PathVariable Long idUsuario1, @PathVariable Long idUsuario2) {
        List<Usuario> listaUsuarios = new ArrayList<>();
        Usuario usuario1 = usuarioRepository.findById(idUsuario1).get();
        Usuario usuario2 = usuarioRepository.findById(idUsuario2).get();

        listaUsuarios.add(usuario1);
        listaUsuarios.add(usuario2);

        if (chatRepository.findAllByNome(
                        listaUsuarios.get(0).getApelido() + " e " + listaUsuarios.get(1).getApelido()).get()
                .toArray().length == 0 &&
                chatRepository.findAllByNome(
                                listaUsuarios.get(1).getApelido() + " e " + listaUsuarios.get(0).getApelido()).get()
                        .toArray().length == 0) {
            return ResponseEntity.ok("Chat não existe");
        } else {
            return ResponseEntity.badRequest().body("Chat já existe");
        }


    }
}
