<template>
    <div class="">
        <div class="areas-container">
            <main>
                <div v-if="!successful" class="form-container">
                    <Form class="mb-5" @submit="handleRegister" :validation-schema="schema">
                        <h2 class="mb-2 text-center">Cadastro</h2>
                        <div class="form-group">
                            <!-- <label for="apelido">Apelido</label> -->
                            <Field name="apelido" placeholder="Apelido" type="text" class="form-control" />
                            <ErrorMessage name="apelido" class="error-feedback" />
                            <Field name="nome" placeholder="Nome" type="text" class="form-control" />
                            <ErrorMessage name="nome" class="error-feedback" />
                        </div>
                        <div class="form-group">
                            <Field name="senha" placeholder="Senha" type="password" class="form-control" />
                            <ErrorMessage name="senha" class="error-feedback" />
                            <Field name="email" placeholder="Email" type="text" class="form-control" />
                            <ErrorMessage name="email" class="error-feedback" />
                        </div>
                        <div class="form-group">
                            <Field name="bio" placeholder="Biografia" type="text" class="form-control" />
                            <ErrorMessage name="bio" class="error-feedback" />
                            <Field name="discord" placeholder="Discord" type="text" class="form-control" />
                            <ErrorMessage name="discord" class="error-feedback" />
                        </div>
                        <div class="form-group">
                            <Field name="teamSpeak" placeholder="TeamSpeak" type="text" class="form-control" />
                            <ErrorMessage name="teamSpeak" class="error-feedback" />
                            <Field name="steam" placeholder="Steam" type="text" class="form-control" />
                            <ErrorMessage name="steam" class="error-feedback" />
                        </div>
                        <div class="form-group">
                            <!-- <label for="apelido">Apelido</label> -->
                            <Field name="epic" placeholder="Epic" type="text" class="form-control" />
                            <ErrorMessage name="epic" class="error-feedback" />
                        </div>
                        <div class="form-group">
                            <button class="btn btn-primary btn-block" :disabled="loading">
                                <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                                <span>Confirmar</span>
                            </button>
                        </div>
                    </Form>
                </div>
                <div>
                    <div v-if="message" class="alert" :class="successful ? 'alert-success' : 'alert-danger'">
                        {{ message }}
                    </div>
                </div>
            </main>
            <aside>
                <div class="img-container">
                    <img src="@/assets/SORAKA.png" alt="Soraka League of Legends">
                </div>
            </aside>
        </div>
    </div>
</template>
  
<script>
import { Form, Field, ErrorMessage } from 'vee-validate';
import * as yup from 'yup';

export default {
    name: "RegisterPage",
    components: {
        Form,
        Field,
        ErrorMessage,
    },
    data() {
        const schema = yup.object().shape({
            apelido: yup
                .string()
                .required("Apelido é obrigatório!")
                .min(3, "Deve ter ao menos 3 caracteres!")
                .max(20, "Deve ter no máximo 20 caracteres!"),
            nome: yup
                .string()
                .required("Nome é obrigatório!")
                .min(8, "Deve ter ao menos 8 caracteres!")
                .max(30, "Deve ter no máximo 30 caracteres!"),
            senha: yup
                .string()
                .required("Senha é obrigatória!")
                .min(6, "Deve ter ao menos 6 caracteres!")
                .max(40, "Deve ter no máximo 40 caracteres!"),
            email: yup
                .string()
                .required("Email é obrigatório!")
                .email("Email é inválido")
                .max(50, "Deve ter no máximo 50 caracteres!"),
            discord: yup
                .string(),
            teamSpeak: yup
                .string(),
            steam: yup
                .string(),
            epic: yup
                .string()
        });

        return {
            successful: false,
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
    mounted() {
        if (this.loggedIn) {
            this.$router.push("/perfil");
        }
    },
    methods: {
        handleRegister(usuario) {
            this.message = "";
            this.successful = false;
            this.loading = true;

            this.$store.dispatch("auth/cadastrar", usuario).then(
                (data) => {
                    this.message = data.message;
                    this.successful = true;
                    this.loading = false;
                },
                (error) => {
                    this.message =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                    this.successful = false;
                    this.loading = false;
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

main, aside{
    margin-top: 25px;
}

.areas-container {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: row;
    height: 79vh;
    width: 90vw;
}

aside {
    display: flex !important;
    align-items: center !important;
    justify-content: center !important;
    width: 45vw !important;
    height: 100vh !important;
}

main {
    flex-direction: column;
    display: flex !important;
    align-items: center !important;
    justify-content: center !important;
    width: 45vw !important;
    height: 80vh !important;
}

Form {
    width: 100%;
    margin: auto;
    /*box-shadow: 2px 2px 3px 2px rgba(4, 4, 4, 0.3);*/
    padding: 20px;
    border-radius: 10px;
}

.form-control {
    margin: 5px !important;
    width: 40%;
}

.form-group{
    display: flex  !important;;

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
    width: 40vw;
    height: 70vh;
    background-color: #8031FF;
    border-radius: 10px;
    background-image: url('@/assets/roxo.png');
    object-fit: cover;
    background-size: 120%;

}

.img-container img {
    height:  ;
    width: 120% !important;
    overflow: visible;
}

#mg-logo {
    width: 200px;
    height: 200px;
}

.logo-container {
    height: 10vh;
    display: flex;
    align-items: center;
    justify-content: center;
    width: 100%;
}

.form-container {
    width: 100%;
}
</style>