import axios from 'axios'
import authHeader from './auth-header'

const API_URL = "http://localhost:8080/comentarios/"

class ComentariosService {
    findAllComentarios(idPost) {
        return axios.get(API_URL + `${idPost}`, { headers: authHeader() });
    }
    saveComentario(comentario, idPost, idUsuario){
        return axios.post(API_URL + "salvar", 
        {   
            conteudo: comentario.conteudo,
            post: { id: idPost},
            usuario: { id: idUsuario}
        },
        {headers: authHeader()})
    }
    deleteComentario(idComentario){
        return axios.delete(API_URL + `deletar/${idComentario}`, {headers: authHeader()})
    }
}


export default new ComentariosService();