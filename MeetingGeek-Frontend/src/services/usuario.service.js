import axios from 'axios'
import authHeader from './auth-header'

const API_URL = "http://localhost:8080/test/"

class UsuarioService {
    getPublicContent(){
        return (axios.get(API_URL + "all"));
    }
    getModeratorContent(){
        return axios.get(API_URL + "mod", {headers:authHeader()});
    }
    getUserContent(){
        return axios.get(API_URL + "user", {headers:authHeader()});
    }

}

export default new UsuarioService();   