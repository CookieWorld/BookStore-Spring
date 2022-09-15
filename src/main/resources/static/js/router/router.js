import Vue from 'vue'
import VueRouter from 'vue-router'
import BookList from "../pages/BookList.vue";
import Login from "../pages/Login.vue";
import Registration from "../pages/Registration.vue";

Vue.use(VueRouter)

const routes = [
    {path: '/', component: BookList },
    {path: '/login', component: Login },
    {path: '/registration', component: Registration},
    {path: '*', component: BookList}
]

export default new VueRouter({
    mode: 'history',
    routes
})