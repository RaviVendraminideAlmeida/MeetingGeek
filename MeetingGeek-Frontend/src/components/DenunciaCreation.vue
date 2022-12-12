<template>
    <div class="form-container">
        <Form id="form" @submit="saveDenuncia" :validation-schema="schema">
            <h2 class="mb-2 text-center">Denunciar</h2>
            <div>

            </div>
            <div class="form-group">
                <Field name="motivo" placeholder="Motivo" class="form-control" />
                <ErrorMessage name="motivo" class="error-feedback" />
            </div>
            <div class="form-group">
                <Field name="detalhes" as="textarea" placeholder="Detalhes" class="form-control" />
                <ErrorMessage name="detalhes" class="error-feedback" />
            </div>
            <div class="form-group" id="btn-container">
                <button id="create-btn" class="btn btn-warning">
                    Confirmar
                </button>
            </div>
        </Form>
    </div>
</template>

<script>
import { Form, Field, ErrorMessage } from 'vee-validate';
import * as yup from 'yup';
import denunciaService from '@/services/denuncia.service';

export default {
    name: "DenunciaCreation",
    components: {
        Form,
        Field,
        ErrorMessage
    },
    data() {
        const schema = yup.object().shape({
            motivo: yup
                .string()
                .required("Motivo é obrigatório!")
                .max(100, "Deve ter no máximo 100 caracteres!"),
            detalhes: yup
                .string()
                .required("Detalhes são obrigatórios!")
                .max(510, "Deve ter no máximo 510 caracteres!"),
                });
        return {
            id: 0,
            schema
        }
    },
    created(){
        this.id = this.$route.params.id;
    },
    methods: {
        saveDenuncia(denuncia) {
            console.log(this.id);
            denunciaService.saveDenuncia(denuncia, this.id);
            this.$router.push("/posts");
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

.form-grup {
    flex-direction: column !important;
}


#create-btn {
    width: 99%;
}
</style>


