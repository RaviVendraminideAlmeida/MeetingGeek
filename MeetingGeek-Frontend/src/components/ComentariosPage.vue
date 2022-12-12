<template>
    <main>
            <div class="comentarios-container">
                <div v-for="comentario in comentarios" :key="comentario" class="comentario-card">
                    <div v-if="isComentarioFromUser(comentario.usuario.id)" class="actions-container">
                        <div class="delete-container">
                            <img src="@/assets/delete_icon.png" alt="">
                            <span  v-on:click="deleteComentario(comentario.id)">Deletar</span>
                        </div>
                    </div>
                    <div class="usuario-container">
                        <img src="@/assets/USER_ICON.png" alt="">
                        <p id="nome-usuario">{{comentario.usuario.nome}}</p>
                    </div>    
                    {{comentario.conteudo}}
                </div>
            </div> 
            <Form @submit="saveComentario" :validation-schema="schema" class="form">
                <div class="form-group" id="field-container">
                    <Field class="form-field" id="campo-conteudo" as="textarea" name="conteudo" placeholder="Conteúdo"/>
                    <button class="btn btn-primary botao-salvar">Salvar</button>
                </div>
                <ErrorMessage class="text-center" name="conteudo"/>
            </Form>
    </main>
</template>

<style>
    main{
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        width: 100% !important;
    }
    .form{
        width:50% !important;
        padding: 10px;
    }
    .botao-salvar{
        width: 15% !important;
        height: 100%;
        margin: 5px;
    }
    #field-container{
        display: flex !important;
        align-items: center !important;
        justify-content: center !important;
        height: 100% !important;
        width: 100% !important;
    }
    #campo-conteudo{
        width: 100%;
        height: 100%;
    }
    .comentarios-container{
        width: 50%;
        overflow-y: scroll;
        height: 100%;
    }
    .usuario-container{
        display: flex;
        align-items: center;
        justify-content: flex-start;
    }
    .usuario-container img {
        width: 30px;
        height: 30px;
    }
    #nome-usuario{
        margin-top: 16px;
        margin-left: 10px;
        font-weight: bold;
        color: #8031FF;
    }
    .comentario-card{
        box-shadow: 1px 2px 2px 2px rgba(0,0,0,0.3);
        padding: 15px;
        border-radius: 10px;
        margin: 10px;
    }
    main{
        /*box-shadow: 1px 2px 2px 2px rgba(0,0,0,0.3);*/
    }
    .delete-container{
        cursor: pointer;
    }
    .delete-container img{
        width: 25px;
        height: 25px;
    }
    .delete-container span{
        margin-left: 5px;
        color:#D30000 ;
    }
</style>

<script>
import { Form, Field, ErrorMessage } from 'vee-validate';
import * as yup from 'yup';
import comentarioService from '@/services/comentarios.service';


export default {
    name: "ComentariosPage",
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
                .max(50, "Deve ter no máximo 120 caracteres!")});
        return {
            comentarios: [],
            idPost: 0,
            schema
        }
    },
    mounted(){
        comentarioService.findAllComentarios(this.idPost).then(
            (response) => {
                this.comentarios = response.data.reverse();
            },
            (error) => {
                this.posts =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();
            }
        );
    },
    created(){
        this.idPost = this.$route.params.idPost;
    },
    methods: {
        saveComentario(comentario) {
            comentarioService.saveComentario(comentario, this.idPost, this.$store.state.auth.usuario.id);
            this.$router.go();
        },
        isComentarioFromUser(idUsuario){
            return this.$store.state.auth.usuario.id === idUsuario;
        },
        deleteComentario(comentarioId){
            comentarioService.deleteComentario(comentarioId);
            this.$router.go();
        }
    }
}
</script>