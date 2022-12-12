<template>
    <div class="container">
      <header class="jumbotron">
        <h3>
          <strong>{{currentUsuario.apelido}}</strong> Perfil
        </h3>
      </header>
      <!-- <p>
        <strong>Token:</strong>
        {{currentUsuario.token.substring(0, 20)}} ... {{currentUsuario.token.substr(currentUsuario.token.length - 20)}}
      </p>
      <p>
        <strong>Id:</strong>
        {{currentUsuario.id}}
      </p> -->
      <p> 
        <strong>Nome:</strong>
        {{currentUsuario.nome}}
      </p>
      <p> 
        <strong>Email:</strong>
        {{currentUsuario.email}}
      </p>
      <p>
        <strong>Bio:</strong>
        {{currentUsuario.bio}}
      </p>
      <p>
        <strong>Discord:</strong>
        {{currentUsuario.discord}}
      </p>
      <p>
        <strong>TeamSpeak:</strong>
        {{currentUsuario.teamSpeak}}
      </p>
      <p>
        <strong>Steam:</strong>
        {{currentUsuario.steam}}
      </p>
      <strong>Cargos:</strong>
      <ul>
        <li v-for="cargo in currentUsuario.cargos" :key="cargo">{{
          cargo.split("_")[1]
          }}</li>
      </ul>
      <strong>Jogos</strong>
      <ul>
        <li v-for="jogo in currentUsuario.jogos" :key="jogo">{{jogo.nome}}</li>
      </ul>
    </div>
  </template>
  
  <script>

  export default {
    name: 'ProfilePage',
    computed: {
      currentUsuario() {
        return this.$store.state.auth.usuario;
      }
    },
    mounted() {
      if (!this.currentUsuario) {
        this.$router.push('/login');
      }
    }
  };
    </script>