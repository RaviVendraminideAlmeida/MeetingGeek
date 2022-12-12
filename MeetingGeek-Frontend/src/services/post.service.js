import axios from 'axios'
import authHeader from './auth-header'

const API_URL = "http://localhost:8080/posts/"

class PostService {
    findAllPosts() {
        return axios.get(API_URL, { headers: authHeader() });
    }
    findPostById(idPost){
        return axios.get(API_URL + `carregar/${idPost}`)
    }
    likePost(idPost) {
        axios.get(API_URL + `curtir/${idPost}`, { headers: authHeader() });
    }
    savePost(post) {
        var usuario = JSON.parse(localStorage.getItem('usuario'));
        console.table(usuario);

        axios.post(API_URL + "salvar", {
            conteudo: post.conteudo,
            usuario: {
                id: usuario.id,
                apelido: usuario.apelido
            }
        }, {headers: authHeader()})
    }
    updatePost(post, idPost) {
        var usuario = JSON.parse(localStorage.getItem('usuario'));
        console.table(usuario);

        axios.put(API_URL + `alterar/${idPost}`, {
            id: idPost,
            conteudo: post.conteudo,
        }, {headers: authHeader()})
    }
    deletePost(idPost){
        axios.delete(API_URL + `delete/${idPost}`, {headers: authHeader()})
    }
    findAllPostsByUsuario(idUsuario){
        return axios.get(API_URL + `procurar/${idUsuario}`, {headers: authHeader()});
    }
}

export default new PostService();