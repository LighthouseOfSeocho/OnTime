import Vue from 'vue';
import Router from 'vue-router';

import PromiseList from '../components/PromiseList.vue'
import Promise from '../components/promise/Promise.vue'
import Login from '../components/Login.vue'

Vue.use(Router);

const router = new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'Home',
            redirect: '/login',
        },
        {
            path: '/my-promise',
            name: 'All of my promises',
            component: PromiseList,
        }
        ,
        {
            path: '/login',
            name: 'Log in',
            component: Login,
        },
        {
            path: '/promise',
            name: 'promiseDetail',
            component: Promise,
        },
        /*
      {
        path: '/board/:id/modify',
        name: 'Board/Modify',
        component: BoardModify,
        props: true,
        beforeEnter: auth.isAuthenticated,
      }
      */
    ],
});

export default router;