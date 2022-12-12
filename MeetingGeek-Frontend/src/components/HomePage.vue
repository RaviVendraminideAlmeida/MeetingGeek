<template>
  <div class="main-container">
    <!-- <div class="title-container">
      <img src="@/assets/home_icon.png" id="home-icon" alt="">
      <h1 class="text-center m-2">Home</h1>
    </div> -->
    <div class="landing-container">
      <img src="@/assets/logo.png" alt="">
      <div>
        <h3 class="text-center text-warning" style="font-style: italic;">Conecte-se de uma maneira diferente</h3>
        <p class="text-center" style="font-style: italic;">Conheça usuários a partir de seus interesses</p>
        <h3 class="purple-txt text-center" style="font-style: italic;">A melhor forma de jogar</h3>
        <p style="font-style: italic;" class="text-center">Sistema de chat integrado</p>
        <RouterLink id="router" class="text-center" to="/login">
          <div class="btn-container">
            <button class="btn btn-primary">Login</button>
          </div>
        </RouterLink>
      </div>
    </div>
    <div class="sobre-nos">
      <div>

        <h1 class="text-center">Sobre nós</h1>
        <p class="text-center">
          <strong>A plataforma Meeting Geek consiste num sistema de conexão de usuários que têm interesse em jogos online, nosso
          papel é melhorar a comunidade de games a fim de estabelecer um padrão de comportamento adequado e divertido.</strong>
        </p>
        <img src="@/assets/VR.png" id="img-vr" alt="">
      </div>
    </div>

    <div class="redes-sociais mt-4">
      <div>

        <h1 class="text-center mt-4">Redes Sociais</h1>
        <div class="midia-container">
          <a class="social-link" href="https://facebook.com/meetinggeek">
            <div class="social-container">
              <img class="social-img" src="@/assets/facebook.png" alt="">
            </div>
          </a>
          <a class="social-link" href="https://www.instagram.com/meetinggeek/">
            <div class="social-container">
              <img class="social-img" style="width: 53px; height: 53px;" src="@/assets/instagram.png" alt="">
            </div>
          </a>
        </div>
      </div>
    </div>

    <footer>
      <p class="text-center">Ⓒ MeetingGeek</p>
    </footer>

  </div>
</template>
  
<style>
.landing-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
  height: 90vh;
}

footer {
  height: 5vh;
  display: flex;
  align-items: center;
  justify-content: center;

}

.main-container {
  width: 100%;
}

.social-img {}

.sobre-nos {
  height: 50vh;
  display: flex;
  align-items: center;
  justify-content: center;
}

.redes-sociais {
  height: 30vh;
  display: flex;
  align-items: center;
  justify-content: center;

}

.sobre-nos img {
  width: 100%;
}

#img-vr {
    box-shadow: 2px 2px 2px 2px rgba(0, 0, 0, 0.5);
}

.sobre-nos p {
  font-size: 1rem;
}

#home-icon {
  width: 35px;
  height: 35px;
}

.purple-txt {
  color: #8031FF;
}

.title-container {
  display: flex;
  align-items: center;
  justify-content: center;

}

.btn-primary {
  width: 100% !important;
  margin: auto;
}

.redes-sociais p {
  font-size: 2rem;
}

.social-container img {
  width: 40px;
  height: 40px;
}

#router {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}

.social-container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: row;
}


.social-container>* {
  margin: 5px;
}

.social-link {
  text-decoration: none;
  color: #8031FF;
}

.midia-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
}
</style>

<script>
import usuarioService from '../services/usuario.service';

export default {
  name: "HomePage",
  data() {
    return {
      content: "",
    };
  },
  mounted() {
    usuarioService.getPublicContent().then(
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
  }
};

</script> 