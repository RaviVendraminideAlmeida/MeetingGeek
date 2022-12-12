import axios from 'axios'
import authHeader from './auth-header'

const API_URL = "http://localhost:8080/chat/"

const usuario = JSON.parse(localStorage.getItem('usuario'));

class ChatService {
    findChatsByUsuario() {
        const response = axios.get(API_URL + `procurar/${usuario.id}`, { headers: authHeader() });
        return response;
    }
    createChat(usuarioId) {
        const chat = {usuarios: [{id: usuario.id},{id:usuarioId}]}
        axios.post(API_URL + "salvar", chat, {headers: authHeader()});
    }
    findChatById(idChat) {
        return axios.get(API_URL + `carregar/${idChat}`, {headers : authHeader()});
    }
    match(idJogo){
        return axios.get(API_URL + `match/${usuario.id}/${idJogo}`, {headers: authHeader()});
    }
}


export default new ChatService();