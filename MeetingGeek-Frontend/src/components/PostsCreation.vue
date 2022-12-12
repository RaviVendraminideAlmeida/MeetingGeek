<template>
    <div class="form-container">
        <Form @submit="savePost" :validation-schema="schema">
            <h2 v-if="(this.$route.params.idPost == undefined)" class="mb-2 text-center">Criação de Post</h2>
            <h2 v-if="(this.$route.params.idPost != undefined)" class="mb-2 text-center">Alteração de Post</h2>
            <div class="form-group">
                <!-- <label for="apelido">Apelido</label> -->
                <Field name="conteudo" placeholder="Conteúdo" as="textarea" value="" class="form-control" />
                <ErrorMessage name="nome" class="error-feedback" />
            </div>
            <div class="form-group" id="btn-container">
                <button class="btn btn-warning">
                    Criar
                </button>
            </div>
        </Form>
    </div>
</template>

<script>
import { Form, Field, ErrorMessage } from 'vee-validate';
import * as yup from 'yup';
import PostService from '@/services/post.service'

export default {
    name: "PostsCreation",
    components: {
        Form,
        Field,
        ErrorMessage
    },
    data() {
        const schema = yup.object().shape({
            conteudo: yup
                .string()
                .required("Conteúdo é obrigatório!")
                .max(510, "Deve ter no máximo 510 caracteres!"),
        });
        return {
            schema,
        }
    },
    methods: {
        savePost(post) {
            if(this.$route.params.idPost != undefined){
                PostService.updatePost(post, this.$route.params.idPost)
            } else {
                PostService.savePost(post)
            }
            this.$router.push("/posts")
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

Form{
    margin-top: 120px;
}

</style>


