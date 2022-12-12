import axios from 'axios'
import authHeader from './auth-header'

const API_URL = "http://localhost:8080/mensagens/"

class ComentariosService {
    saveMensagem(mensagem, idChat, idUsuario) {
        return axios.post(API_URL + 'salvar', 
        {
            conteudo: mensagem.conteudo,
            chat: {id: idChat},
            usuario: {id: idUsuario}
        }, {headers: authHeader()})
    }
}


export default new ComentariosService();