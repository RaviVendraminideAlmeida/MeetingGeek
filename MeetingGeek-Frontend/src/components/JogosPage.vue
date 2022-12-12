<template>
    <div class="all-container">
        <div class="btn-create-container" v-if="isModerator">
            <RouterLink to="jogos/salvar">
                <button id="create-btn" class="btn btn-warning">+ Cadastrar</button>
            </RouterLink>
        </div>

        <h2 class="text-center m-3" v-if="(jogos.length == 0)">Nenhum jogo cadastrado</h2>

        <div v-for="jogo in jogos" :key="jogo" class="game-card">
            <div class="game-container">
                <div class="text-container">
                    <p v-if="jogo.nome"><span style="color:#8031FF ">Nome: </span> {{ jogo.nome }}</p>
                </div>
                <div class="actions-container">
                    <button v-if="!isJogoInUser(jogo)" class="btn btn-primary" v-on:click="followJogo(jogo)" >Seguir</button>
                    <button v-if="isJogoInUser(jogo)" class="btn btn-warning" v-on:click="match(jogo)">Match</button>
                    <button v-if="isModerator" v-on:click="deleteJogo(jogo)" class="btn btn-danger">Desativar</button>
                </div>
            </div>

        </div>
    </div>

</template>

<script>
import jogosService from '@/services/jogos.service';
import chatService from '@/services/chat.service';

export default {
    name: "JogosPage",
    computed: {
        currentUsuario() {
            return this.$store.state.auth.usuario;
        },
        isModerator() {
            if (this.currentUsuario && this.currentUsuario['cargos']) {
                return this.currentUsuario['cargos'].includes('ROLE_MODERADOR');
            }

            return false;
        }
    },
    data() {
        return {
            jogos: [],
            resposta: {},
        };
    },
    mounted() {
        console.log(jogosService.findAllJogos())
        jogosService.findAllJogos().then(
            (response) => {
                this.jogos = response.data;
            },
            (error) => {
                this.jogos =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
            }
        );
    },
    methods: {
        deleteJogo(jogo) {
            jogosService.deleteJogo(jogo.id);
            const indexOfObject = this.jogos.findIndex(objJogo => { return objJogo.id == jogo.id; })
            this.jogos.splice(indexOfObject, 1);
        },
        followJogo(jogo){
            jogosService.followJogo(jogo.id, this.$store.state.auth.usuario.id);
            this.$store.state.auth.usuario.jogos.push(jogo);
        },
        isJogoInUser(jogo){
            var jogos = this.$store.state.auth.usuario.jogos;
            
            for(var i = 0; i < jogos.length; i++){
                if(jogo.nome == jogos[i].nome){
                    return true
                }
            }
            return false;
        },
        match(jogo){
            chatService.match(jogo.id).then((response) => this.reposta = response.data);

            if (this.resposta.id != undefined){
                this.$router.push({path: "/chat", params: {idChat : this.resposta.id}})
            } else {
                this.$router.push("/chats");
            }
        }
    }
};


</script>

<style>
.all-container {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}


.game-container {
    width: 100%;
    display: flex;
    align-items: center;
    flex-direction: row;
}

.actions-container {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    flex-direction: row;
    width: 50%;
}

.actions-container button {
    margin-left: 5px;
}

.text-container {
    width: 50%;
    margin-top: 20px;
}

.game-card {
    box-shadow: 1px 1px 3px 1px rgba(0, 0, 0, 0.3);
    margin: 10px;
    padding: 15px;
    display: flex;
    align-items: center;
    justify-content: flex-start;
    flex-direction: column;
    border-radius: 10px;
    width: 50%;
}

.btn-create-container {
    margin: 10px;
    width: 50%;
}
</style>