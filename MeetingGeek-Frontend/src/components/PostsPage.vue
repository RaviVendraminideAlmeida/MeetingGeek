<template>

    <div class="posts-container">

        <div class="btn-container">
            <RouterLink to="/posts/criar">
                <button id="btn-create" class="btn btn-warning">+ Criar</button>
            </RouterLink>
        </div>

        <h3 v-if="(posts.length == 0)">Nenhum Post Disponível</h3>

        <div v-for="post in posts" :key="post.numeroLikes" class="post-card">
            <div class="options-container" v-if="post.usuario">
                <RouterLink v-if="!isPostFromUser(post)" style="color: #D30000" :to="{name: 'cadastroDenuncia', params: {id : post.usuario.id}}">
                    <img style="width: 30px; height: 30px;" src="@/assets/alerta.png" />
                    Denunciar
                </RouterLink>
                <div v-if="!isPostFromUser(post)" v-on:click="createChat(post.usuario.id)" class="chat-option">
                    <img src="@/assets/chat_icon.png"/>
                    Chat
                </div>
                <div v-if="isPostFromUser(post)" v-on:click="deletePost(post)" class="delete-option">
                    <img src="@/assets/delete_icon.png" alt="">
                    Deletar
                </div>
                <RouterLink v-if="isPostFromUser(post)" style="color: #8031FF; margin-left: 10px; display: flex; align-items: center; justify-content: center;" :to="{name: 'alteracaoPost', params: {idPost : post.id}}">
                    <img style="width: 28px; height: 28px;" src="@/assets/pencil_icon.png" />
                    <span>Alterar</span>
                </RouterLink>

            </div>

            <div class="name-container">
                <img src="@/assets/USER_ICON.png" id="user_icon" alt="">
                <p v-if="post.usuario">{{ post.usuario.nome }}</p>
            </div>
            <p>{{ post.conteudo }}</p>
            <div class="likes-container">
                <span>{{ post.numeroLikes }}</span>
                <img src="@/assets/LIKE_ROXO.png" />
                <span id="coments-number">{{ post.comentarios.length }}</span>
                <img src="@/assets/COMENTARIO_ROXO.png" />
            </div>
            <div class="actions-container mt-2">
                <button v-on:click="likePost(post)" id="like-btn" class="btn btn-primary">Curtir</button>
                <RouterLink id="comment-router" :to="{name: 'comentariosPage', params: {idPost : post.id}}">
                    <button id="coment-btn" class="btn btn-warning">Comentar</button>
                </RouterLink>
            </div>
        </div>
    </div>


</template>

<script>
import PostService from "../services/post.service"
import ChatService from "../services/chat.service";

export default {
    name: "PostsPage",
    data() {
        return {
            posts: [],
        };
    },
    mounted() {
        PostService.findAllPosts().then(
            (response) => {
                this.posts = response.data.reverse();
            },
            (error) => {
                this.posts =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
            }
        );
    },
    methods: {
        likePost(post) {
            PostService.likePost(post.id);
            post.numeroLikes++;
        },
        createChat(idUsuario){
            ChatService.createChat(idUsuario);
            this.$router.push("/chats");
        },
        isPostFromUser(post){
            return post.usuario.id == this.$store.state.auth.usuario.id;
        },
        deletePost(post){
            PostService.deletePost(post.id);
            this.$router.go();
        }
    },
    computed: {
        currentUsuario() {
            return this.$store.state.auth.usuario;
        }, 
    }
}

</script>

<style>
template {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100vw;
}

.actions-container {
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

.options-container{
    display: flex;
    align-items: center;
    justify-content: flex-start;
    flex-direction: row;
}

options-container > *{
}

#like-btn {
    width: 100% !important;
}

.btn-container {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    width: 50%;
    margin: 10px;
}

#user_icon {
    width: 20px;
    height: 20px;
}

#coments-number {
    margin-left: 10px;
    margin-right: 5px;
}

#coment-btn {
    text-align: center;
    width: 80% !important;
    margin-left: 30px;
}

#comment-router{
    width: 40% !important;
}

ul {
    list-style: none;
}

.posts-container {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
    width: 100%;
}

.name-container {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    flex-direction: row;
}

.name-container p {
    margin-top: 16px;
    margin-left: 10px;
    font-weight: bold;
    color: #8031FF;
}

.delete-option{
    display: flex;
    align-items: center;
    justify-content: center;
    color: #D30000;
    cursor: pointer;
}

.delete-option img {
    width: 35px;
    height: 35px;
    margin-right: 5px;
}

.post-card {
    box-shadow: 0.5px 1px 1px 1px rgba(0, 0, 0, 0.3);
    padding: 20px;
    margin: 15px;
    display: flex;
    justify-content: center;
    flex-direction: column;
    width: 50%;
    border-radius: 10px;
}

.likes-container img {
    height: 25px;
    width: 25px;
}

.likes-container {
    display: flex;
    align-items: center;
    justify-content: flex-start;
}

.chat-option{
    margin-left: 10px;
    color: #8031FF;
    cursor: pointer;
}


.chat-option img {
    width: 30px;
    height: 30px;
}
</style>