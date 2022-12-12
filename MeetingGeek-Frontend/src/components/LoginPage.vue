<template>
  <div class="">
    <div class="areas-container">
      <main>
        <!-- <div class="logo-container">
          <img src="@/assets/logo.png" id="mg-logo" alt="Meeting Geek Logo">
        </div> -->
        <Form class="mb-5" @submit="handleLogin" :validation-schema="schema">
          <h1 class="mb-2 text-center">Login</h1>
          <div class="form-group">
            <!-- <label for="apelido">Apelido</label> -->
            <Field name="apelido" placeholder="Apelido" type="text" class="form-control" />
            <ErrorMessage name="apelido" class="error-feedback" />
          </div>
          <div class="form-group">
            <!-- <label for="senha">Senha</label> -->
            <Field name="senha" placeholder="Senha" type="password" class="form-control" />
            <ErrorMessage name="password" class="error-feedback" />
          </div>
          <div class="form-group">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span v-show="loading" class="spinner-border spinner-border-sm"></span>
              <span>Confirmar</span>
            </button>
          </div>
          <div class="register-link">
            <router-link to="/cadastro" class="nav-link">
              <a href="#">Não tem cadastro? Clique Aqui</a>
            </router-link>
          </div>
        </Form>
        <div class="form-group">
          <div v-if="message" class="alert alert-danger" role="alert">
            {{ message }}
          </div>
        </div>
      </main>
      <aside>
        <div class="img-container">
          <img src="@/assets/REYNA.png" id="reyna-img" alt="Reyna Valorant">
        </div>
      </aside>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from 'vee-validate';
import * as yup from 'yup';

export default {
  name: "LoginPage",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      apelido: yup.string().required("Apelido é obrigatório!"),
      senha: yup.string().required("Senha é obrigatória!"),
    });

    return {
      loading: false,
      message: "",
      schema,
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/perfil");
    }
  },
  methods: {
    handleLogin(usuario) {
      this.loading = true;

      this.$store.dispatch("auth/login", usuario).then(
        () => {
          this.$router.push("/perfil");
        },
        (error) => {
          this.loading = false;
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
        }
      );
    },
  },
};
</script>

<style>
.btn-primary {
  background-color: #8031FF !important;
}

.areas-container {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
  height: 80vh !important;
  width: 90vw;
}

aside {
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
  width: 50vw !important;
  height: 80vh !important;
}

main {
  flex-direction: column;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
  width: 50vw !important;
  height: 80vh !important;
}

Form {
  width: 60% !important;
  margin: auto;
  box-shadow: 2px 2px 3px 2px rgba(4, 4, 4, 0.3);
  padding: 50px;
  border-radius: 10px;
}

.form-control {
  margin: 0 !important;
}

.register-link {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  flex-direction: row;
}

.img-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 90%;
  height: 90%;
  background-color: #8031FF;
  border-radius: 10px;
  background-image: url('@/assets/roxo.png');
  object-fit: cover;
  background-size: 120%;

}

#reyna-img {
  width: 80% !important;
  height: 130% !important;
  overflow: visible;
}

#mg-logo {
  width: 200px;
  height: 200px;
}

.logo-container {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  margin-top: 30px;
}
</style>