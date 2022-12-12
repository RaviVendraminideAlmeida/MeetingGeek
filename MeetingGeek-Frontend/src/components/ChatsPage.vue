<template>
    <main>
        <h3 v-if="(chats.length == 0)">Nenhum chat disponível</h3>

        <div class="chats">
            <div class="chat-container m-2" v-for="chat in chats" :key="chat">
                <RouterLink class="chat-router" :to="{ name: 'chatDetails', params: { idChat: chat.id } }">
                    <div class="chat-img-container">
                        <img id="chat-img" src="@/assets/chat_icon.png" alt="">
                    </div>
                    <div class="left-container">
                        <span id="nome-container">{{ chat.nome }}</span>
                        <div id="mensagens-container" v-if="(chat.mensagens.length != 0)">
                            <span class="mensagem-usuario">{{ chat.mensagens[chat.mensagens.length - 1].usuario.nome }} : </span>
                            <span class="mensagem">{{ chat.mensagens[chat.mensagens.length - 1].conteudo }}</span>
                        </div>
                    </div>
                </RouterLink>
            </div>
        </div>
    </main>
</template>

<style>
main {
    width: 100% !important;
    display: flex;
    align-items: center;
    justify-content: center;
}

.chats {
    width: 50%;
    height: 100%;
    box-shadow: 1;
}

.chat-container {
    box-shadow: 1px 1px 3px 1px rgba(0, 0, 0, 0.5);
    border-radius: 10px;
    padding: 20px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

.mensagem{
    color: rgb(0,0,0);
}

.chat-img-container {
    width: 10%;
    height: 100%;
}

#mensagens-container{
    margin-left: 40px;
}

#nome-container {
    font-weight: bold;
    color: #8031FF;
    margin-left: 40px !important;
}

#chat-img {
    width: 40px !important;
    height: 40px !important;
}

.mensagem-usuario{
    color: #EFCF20;
    font-weight: bold;
}

.chat-router {
    display: flex;
    align-items: center;
    justify-content: flex-start;
}
</style>

<script>
import ChatService from "../services/chat.service";

export default {
    name: "ChatsPage",
    data() {
        return {
            chats: [],
        };
    },
    mounted() {
        ChatService.findChatsByUsuario(this.$store.state.auth.usuario.id).then(
            (response) => {
                this.chats = response.data.reverse();
            },
            (error) => {
                this.chats =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
            }
        );
    }
}

</script>

