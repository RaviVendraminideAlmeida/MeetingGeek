<template>
    <div class="main-container">
        <main id="container-principal">
            <div class="nome-chat-container">
                <h3 class="text-left m-3 nome-chat">{{ chat.nome }}</h3>
                <div class="modal-btn-container">
                    <button type="button" class="btn" id="botao-modal" data-toggle="modal" data-target="#exampleModalLong">
                        Contato
                    </button>

                    <!-- Modal -->
                    <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog"
                        aria-labelledby="exampleModalLongTitle" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLongTitle">Contatos</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="corpo-modal">
                                    <div v-for="usuario in chat.usuarios" v-bind:key="usuario">
                                        <h5 v-if="usuario.nome"><strong>Usuário:</strong> {{usuario.apelido}}</h5>
                                        <div id="atributos-container">
                                            <span v-if="usuario.bio"><strong>Bio:</strong> {{usuario.bio}}</span>
                                            <span v-if="usuario.discord"><strong>Discord:</strong> {{usuario.discord}}</span>
                                            <span v-if="usuario.teamSpeak"><strong>TeamSpeak:</strong> {{usuario.teamSpeak}}</span>
                                            <span v-if="usuario.steam"><strong>Steam:</strong> {{usuario.steam}}</span>
                                            <span v-if="usuario.epic"><strong>Epic Games:</strong> {{usuario.epic}}</span>
                                            <span v-if="usuario.email"><strong>Email:</strong> {{usuario.email}}</span>
                                        </div>
                                        <div class="m-3"></div>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div>

            </div>
            <div class="lista-mensagens">
                <div v-for="mensagem in chat.mensagens" :key="mensagem" class="mensagens">
                    <div class="usuario">
                        <img src="@/assets/USER_ICON.png" alt="">
                        <span :style="{ 'color': isLoggedUsuario(mensagem) ? '#EFCF20' : '#8031FF' }">
                            {{ mensagem.usuario.nome }}</span><br />
                    </div>
                    <span>{{ mensagem.conteudo }}</span>
                </div>
            </div>
            <div class="formulario-container">
                <Form :validation-schema="schema" @submit="saveMensagem" id="formulario-comentario">
                    <div>
                        <Field name="conteudo" placeholder="Conteúdo" id="conteudo-field" />
                        <button class="btn btn-warning" id="botao-enviar">Enviar</button>
                    </div>
                    <ErrorMessage name="conteudo" />
                </Form>
            </div>
        </main>
    </div>
</template>

<style>
#atributos-container{
    display: flex;
    flex-direction: column;
}

#container-principal {
    width: 80% !important;
    height: 100% !important;
    box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    border-radius: 25px;
}


.formulario-container {
    width: 100%;
    height: 20%;
    display: flex;
    align-items: center;
    justify-content: center;
}

.modal-btn-container {
    width: 45%;
    display: flex;
    align-items: center;
    justify-content: flex-end !important;
}

#formulario-comentario {
    box-shadow: none;
    margin: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;

}

#botao-modal {
    height: 80%;
    width: 25% !important;
    border: 1px solid rgba(0, 0, 0, 0.5);
    background-color: rgba(240, 240, 240, 0.5);
}

#conteudo-field {
    height: 40px;
    margin-right: 10px;
}

.mensagens {
    border-bottom: 1px solid rgba(0, 0, 0, 0.3);
    border-top: 1px solid rgba(0, 0, 0, 0.3);
    width: 30%;
    padding: 10px;
    margin: 10px;
    border-radius: 20px;
    box-shadow: 1px 2px 2px 1px rgba(0, 0, 0, 0.3);
}

.main-container {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 85vh;
}

.nome-chat-container {
    /*border-bottom: 1px solid rgba(0, 0, 0, 0.5);*/
    width: 100%;
    padding: 5px;
    display: flex;
}

.nome-chat {
    width: 50%;
}

.lista-mensagens {
    width: 100%;
    height: 80%;
    border-top: 1px solid rgba(0, 0, 0, 0.3);
    border-bottom: 1px solid rgba(0, 0, 0, 0.3);
    overflow-y: scroll;
}

#botao-enviar {
    height: 42px !important;
    margin: 0px !important;
}

.usuario {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    flex-direction: row;
    font-weight: bold;
    color: #8031FF;
    font-size: 20px;
}

.usuario img {
    width: 20px;
    height: 20px;
    margin-right: 10px;
}

.formulario {
    height: 15%;
}
</style>

<script>
import { Form, Field, ErrorMessage } from 'vee-validate';
import * as yup from 'yup';
import MensagemService from '@/services/mensagem.service';
import ChatService from '@/services/chat.service';

export default {
    name: "ChatDetails",
    components: {
        Form,
        Field,
        ErrorMessage
    },
    data() {
        const schema = yup.object().shape({
            conteudo: yup
                .string()
                .required("Conteúdo é obrigatório!")
                .max(50, "Deve ter no máximo 120 caracteres!")
        });
        return {
            chat: {},
            idChat: 0,
            schema
        }
    },
    mounted() {
        ChatService.findChatById(this.idChat).then(
            (response) => {
                this.chat = response.data;
            },
            (error) => {
                this.chat =
                (error.response &&
                error.response.data &&
                error.response.data.message) ||
                error.message ||
                error.toString();
            });
        // this.outroUsuario = this.chat.usuarios.find(usuario => usuario.id != this.$store.state.auth.usuario.id);
        // console.log(this.outroUsuario);
    },
    created() {
        this.idChat = this.$route.params.idChat;
    },
    methods: {
        saveMensagem(mensagem) {
            MensagemService.saveMensagem(mensagem, this.idChat, this.$store.state.auth.usuario.id);
            this.$router.go();
        },
        isLoggedUsuario(mensagem) {
            return mensagem.usuario.id == this.$store.state.auth.usuario.id;
        }
    }
}
</script>