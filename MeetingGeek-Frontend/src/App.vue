<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark" style="background-color: #444;">
      <div id="logo-container">
        <img id="logo" src="@/assets/logo.png" alt="">
      </div>
      <a href="/" class="navbar-brand">MeetingGeek</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link to="/home" class="nav-link">
            <font-awesome-icon icon="home" /> Página Inicial
          </router-link>
        </li>
        <li v-if="showModeradorBoard" class="nav-item">
          <router-link to="/mod" class="nav-link">Painel do Moderador</router-link>
        </li>
        <!-- <li class="nav-item">
          <router-link v-if="isUsuario" to="/usuario" class="nav-link">Usuário</router-link>
        </li> -->
        <li class="nav-item">
          <router-link v-if="isUsuario" to="/posts" class="nav-link">Posts</router-link>
        </li>
        <li class="nav-item">
          <router-link v-if="isUsuario" to="/jogos" class="nav-link">Jogos</router-link>
        </li>
        <li class="nav-item">
          <router-link v-if="isUsuario" to="/chats" class="nav-link">Chat</router-link>
        </li>
      </div>

      <div v-if="!currentUsuario" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/cadastro" class="nav-link">
            <font-awesome-icon icon="user-plus" /> Cadastro
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" /> Login
          </router-link>
        </li>
      </div>

      <div v-if="currentUsuario" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/perfil" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUsuario.apelido }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt" /> <span id="logout">Logout</span>
          </a>
        </li>
      </div>
    </nav>

    <div class="container">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  computed: {
    currentUsuario() {
      return this.$store.state.auth.usuario;
    },
    showModeradorBoard() {
      if (this.currentUsuario && this.currentUsuario['cargos']) {
        return this.currentUsuario['cargos'].includes('ROLE_MODERADOR');
      }
      
      return false;
    },
    isUsuario(){
      if (this.currentUsuario && this.currentUsuario['cargos']) {
        return this.currentUsuario['cargos'].includes('ROLE_USUARIO');
      }
      
      return false;
    },
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
  }
};
</script>

<style>
template{
  width: 100vw;
  height: 100vh;
}
#logout{
  color: #FF0000;
}

#logo-container{
  background-color: rgba(0,0,0,0.4);
  border-radius: 50%;
  margin-right: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 60px;
  height: 60px;
}

.navbar, .navbar-expand, .navbar-dark, .bg-dark{
  background-color: #444; 
}

#logo{
  height: 80px;
}
</style>
