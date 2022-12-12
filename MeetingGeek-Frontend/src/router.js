import { createWebHistory, createRouter } from "vue-router";
import HomePage from "./components/HomePage.vue";
import LoginPage from "./components/LoginPage.vue";
import RegisterPage from "./components/RegisterPage.vue";
import PostsCreation from './components/PostsCreation.vue';
import JogoCreation from './components/JogoCreation.vue';
import DenunciaCreation from './components/DenunciaCreation';

// lazy-loaded
const JogosPage = () => import('./components/JogosPage.vue');
const PostsPage = () => import('./components/PostsPage.vue');
const ProfilePage = () => import("./components/ProfilePage.vue");
const ModeradorPage = () => import("./components/ModeradorPage.vue");
const UsuarioPage = () => import("./components/UsuarioPage.vue");
const ComentariosPage = () => import("./components/ComentariosPage.vue");
const ChatsPage = () => import("./components/ChatsPage.vue");
const ChatDetails = () => import("./components/ChatDetails.vue");

const routes = [
  {
    path: "/",
    name: "home",
    component: HomePage,
  },
  {
    path: "/home",
    component: HomePage,
  },
  {
    path: "/login",
    component: LoginPage,
  },
  {
    path: "/cadastro",
    component: RegisterPage,
  },
  {
    path: "/perfil",
    name: "perfil",
    // lazy-loaded
    component: ProfilePage,
  },
  {
    path: "/mod",
    name: "moderador",
    // lazy-loaded
    component: ModeradorPage,
  },
  {
    path: "/usuario",
    name: "usuario",
    // lazy-loaded
    component: UsuarioPage,
  },
  {
    path: "/posts",
    name: "posts",
    // lazy-loaded
    component: PostsPage,
  },
  {
    path:"/posts/criar",
    name: "cadastroPost",
    component: PostsCreation,
  },
  {
    path:"/posts/alterar/:idPost",
    name:"alteracaoPost",
    component: PostsCreation
  },
  {
    path:"/jogos",
    name: "jogos",
    component: JogosPage,
  },
  {
    path:"/jogos/salvar",
    name:"cadastroJogo",
    component: JogoCreation
  },
  {
    path:"/denuncias/:id",
    name:"cadastroDenuncia",
    component: DenunciaCreation
  },
  {
    path:"/comentarios/:idPost",
    name:"comentariosPage",
    component: ComentariosPage
  },
  {
    path: "/chats",
    name: "chatsPage",
    component: ChatsPage
  },
  {
    path: "/chat/:idChat",
    name: "chatDetails",
    component: ChatDetails
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

   router.beforeEach((to, from, next) => {
   const publicPages = ['/login', '/cadastro', '/home'];
   const authRequired = !publicPages.includes(to.path);
   const loggedIn = localStorage.getItem('usuario');

   // trying to access a restricted page + not logged in
   // redirect to login page
   if (authRequired && !loggedIn) {
     next('/login');
   } else {
     next();
   }
 });

export default router;