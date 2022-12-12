import axios from 'axios';

const API_URL = 'http://localhost:8080/auth/';

class AuthService {
    async login(usuario) {
        const response = await axios
            .post(API_URL + 'login', {
                apelido: usuario.apelido,
                senha: usuario.senha
            });
        if (response.data.token) {
            localStorage.setItem('usuario', JSON.stringify(response.data));
            return response.data;
        }


    }

    logout() {
        localStorage.removeItem('usuario');
    }

    cadastrar(usuario) {
        return axios.post(API_URL + 'cadastrar', {
            apelido: usuario.apelido,
            nome: usuario.nome,
            senha: usuario.senha,
            email: usuario.email,
            bio: usuario.bio,
            discord: usuario.discord,
            teamSpeak: usuario.teamSpeak,
            steam: usuario.steam,
            epic: usuario.epic
        })
    }
}

export default new AuthService();