<template>
  <div class="first-container mt">
    <h2 class="text-center m-3" v-if="(denuncias.length == 0)">Nenhuma denúncia foi enviada</h2>
    <div v-for="denuncia in denuncias" :key="denuncia" class="post-card">
      <div id="botao-posts-container">
        <button v-on:click="loadPosts(denuncia.usuario.id)" type="button" class="btn" id="botao-posts"
          data-toggle="modal" data-target="#exampleModalLong">
          Posts
        </button>
      </div>
      <!-- Modal -->
      <div class="modal fade" id="exampleModalLong" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle"
        aria-hidden="true">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="exampleModalLongTitle">Posts de {{ denuncia.usuario.nome }}</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <h5 v-if="(posts.length === 0)">Nenhum post disponível</h5>
              <div v-for="post in posts" :key="post">
                <p><strong>Conteudo: </strong>{{ post.conteudo }}</p>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
          </div>
        </div>
      </div>
      <div id="dados">
        <p><span style="color:#8031FF ">Motivo:</span> {{ denuncia.motivoDenuncia }}</p>
        <p><span style="color:#8031FF ">Detalhes:</span> {{ denuncia.detalhesDenuncia }}</p>
        <div id="usr-container"><img style="margin-right: 5px; margin" id="usr-icon" src="@/assets/USER_ICON.png" alt="">
          <p>Usuário: {{ denuncia.usuario.nome }}</p>
        </div>
      </div>
      <div class="decision-container">
        <button class="btn btn-primary" v-on:click="aceitarDenuncia(denuncia)">Aceitar</button>
        <button class="btn btn-warning" v-on:click="recusarDenuncia(denuncia)">Recusar</button>
      </div>
    </div>
  </div>
</template>
  
<script>
import denunciaService from "@/services/denuncia.service";
import postService from "@/services/post.service";

export default {
  name: "ModeradorPage",
  data() {
    return {
      denuncias: [],
      posts: []
    };
  },
  mounted() {
    denunciaService.findAllDenuncias().then(
      (response) => {
        this.denuncias = response.data;
      },
      (error) => {
        this.content =
          (error.response &&
            error.response.data &&
            error.response.data.message) ||
          error.message ||
          error.toString();
      }
    );
  },
  methods: {
    aceitarDenuncia(denuncia) {
      denunciaService.aceitarDenuncia(denuncia);
      const indexOfObject = this.denuncias.findIndex(objDenuncia => { return objDenuncia.id == denuncia.id; })
      this.denuncias.splice(indexOfObject, 1);
    },
    recusarDenuncia(denuncia) {
      denunciaService.recusarDenuncia(denuncia);
      const indexOfObject = this.denuncias.findIndex(objDenuncia => { return objDenuncia.id == denuncia.id; })
      this.denuncias.splice(indexOfObject, 1);
    },
    loadPosts(idUsuario) {
      postService.findAllPostsByUsuario(idUsuario).then(
        (response) => {
          this.posts = response.data;
          console.log(response.data);
        });
    }
  }
};
</script>

<style>
/* .post-card{
  box-shadow: 2px 2px 2px 2px rgba(0,0,0,0.6);
} */

#dados{
  padding: 10px;
}

.first-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

#botao-posts-container {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 100%;
}

#usr-icon {
  width: 15px;
  height: 15px;
  margin-bottom: 15px;
}

#botao-posts {
  border: 1px solid rgba(0, 0, 0, 0.5);
  margin-bottom: 5px;
  margin-left: 10px;
  width: 20%;
}

#usr-container {
  display: flex;
  align-items: center;
  justify-content: flex-start;
}

.decision-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
}

.decision-container>* {
  margin: 10px;
  width: 50% !important;
}
</style>