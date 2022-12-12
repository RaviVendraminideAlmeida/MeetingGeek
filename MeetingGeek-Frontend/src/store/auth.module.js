import AuthService from "../services/auth.service";

const usuario = JSON.parse(localStorage.getItem('usuario'));

const initialState = usuario
    ? {status : {loggedIn: true}, usuario }
    : {status : {loggedIn: false}, usuario: null};


export const auth = {
    namespaced: true,
    state: initialState,
    actions: {
        login({commit}, usuario) {
            return AuthService.login(usuario).then(
                usuario => {
                    commit('loginSuccess', usuario);
                    return Promise.resolve(usuario);
                },
                error => {
                    commit('loginFailure')
                    return Promise.reject(error);
                }
            );
        },
        logout({commit}){
            AuthService.logout();
            commit('logout');
        },
        cadastrar({commit}, usuario){
            return AuthService.cadastrar(usuario)
                .then(
                    response => {
                        commit("registerSuccess");
                        return Promise.resolve(response.data);
                    },
                    error => {
                        commit('registerFailure');
                        return Promise.reject(error);
                    }
                )
        }
    },
    mutations : {
        loginSuccess(state, usuario){
            state.status.loggedIn = true;
            state.usuario = usuario;
        },
        loginFailure(state){
            state.status.loggedIn = false;
            state.usuario = null;
        },
        logout(state){
            state.status.loggedIn = false;
            state.usuario = null;
        },
        registerSuccess(state){
            state.status.loggedId = false;
        },
        registerFailure(state) {
            state.status.loggedIn = false
        }
    }
};
