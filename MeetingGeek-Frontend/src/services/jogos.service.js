import axios from 'axios'
import authHeader from './auth-header'

const API_URL = "http://localhost:8080/jogos/"

class JogosService {
    findAllJogos() {
        return axios.get(API_URL, { headers: authHeader() });
    }
    saveJogo(jogo){
        return axios.post(API_URL + "cadastrar", 
        {   
            nome: jogo.nome
        },
        {headers: authHeader()})
    }
    deleteJogo(idJogo){
        return axios.delete(API_URL + `desativar/${idJogo}`, {headers: authHeader()});
    }
    followJogo(idJogo, idUsuario){
        axios.get(API_URL + `seguir/${idJogo}/${idUsuario}`, {headers: authHeader()});
    }
    findAllJogosByUsuario(idUsuario){
        return axios.get(API_URL + `procurar/${idUsuario}`, {headers: authHeader()});
    }
}


export default new JogosService();