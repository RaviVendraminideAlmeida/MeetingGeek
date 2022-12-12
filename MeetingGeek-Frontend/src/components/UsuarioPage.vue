<template>
    <div class="container">
      <header class="jumbotron">
        <h3>{{ content }}</h3>
      </header>
    </div>
  </template>
  
  <script>
  import usuarioService from "../services/usuario.service";
  
  export default {
    name: "UsuarioPage",
    data() {
      return {
        content: "",
      };
    },
    mounted() {
      usuarioService.getUserContent().then(
        (response) => {
          this.content = response.data;
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
  };
  </script>