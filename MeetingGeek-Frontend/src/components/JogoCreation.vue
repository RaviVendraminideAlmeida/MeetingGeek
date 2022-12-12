<template>
    <div class="form-container">
        <Form id="form" @submit="saveJogo" :validation-schema="schema">
            <h2 class="mb-2 text-center">Cadastro de Jogos</h2>
            <div class="form-group">
                <!-- <label for="apelido">Apelido</label> -->
                <Field name="nome" placeholder="Nome" class="form-control" />
                <ErrorMessage name="nome" class="error-feedback" />
            </div>
            <div class="form-group" id="btn-container">
                <button id="create-btn" class="btn btn-warning">
                    Criar
                </button>
            </div>
        </Form>
    </div>
</template>

<script>
import { Form, Field, ErrorMessage } from 'vee-validate';
import * as yup from 'yup';
import jogosService from '@/services/jogos.service';

export default {
    name: "JogoCreation",
    components: {
        Form,
        Field,
        ErrorMessage
    },
    data() {
        const schema = yup.object().shape({
            nome: yup
                .string()
                .required("Nome é obrigatório!")
                .max(50, "Deve ter no máximo 50 caracteres!"),
        });
        return {
            schema
        }
    },
    methods: {
        saveJogo(jogo) {
            jogosService.saveJogo(jogo)
            this.$router.push("/jogos")
        },
    }




}
</script>


<style>
template {
    height: 90vh;
    width: 100vw;
    display: flex;
    align-items: center;
    justify-content: center;
}

#btn-container {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
}


#create-btn {
    width: 99%;
}
</style>


