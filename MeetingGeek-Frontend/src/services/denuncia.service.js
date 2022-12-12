import axios from 'axios'
import authHeader from './auth-header'

const API_URL = "http://localhost:8080/denuncias/"

class DenunciaService{
    findAllDenuncias(){
        return axios.get(API_URL , {headers:authHeader()});
    }
    aceitarDenuncia(denuncia){
        return axios.get(API_URL + `aceitar/${denuncia.id}`, {headers:authHeader()})
    }
    recusarDenuncia(denuncia){
        return axios.delete(API_URL + `deletar/${denuncia.id}`, {headers:authHeader()});
    }
    saveDenuncia(denuncia, idUsuario){
        return axios.post(API_URL + "salvar", {
            motivoDenuncia: denuncia.motivo,
            detalhesDenuncia : denuncia.detalhes,
            usuario: {
                id: idUsuario
            }
        }, {headers: authHeader()})
    }
}

export default new DenunciaService();