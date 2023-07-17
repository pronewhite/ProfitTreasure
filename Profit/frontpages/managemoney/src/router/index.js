import { createRouter, createWebHistory } from 'vue-router'

import IndexView from "@/views/IndexView";

const routes = [
  {
    path: '/',
    name: 'IndexView',
    component: IndexView
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/page/product/list',
    name: 'ProductList',
   //懒加载
    component: () => import('../views/ProductList.vue')
  },
  {
    path: '/page/product/detail',
    name: 'ProductDetail',
    //懒加载
    component: () => import('../views/ProductDetail.vue')
  },
  {
    path: '/page/user/register',
    name: 'RegisterView',
    //懒加载
    component: () => import('../views/RegisterView.vue')
  },
  {
    path: '/page/user/login',
    name: 'UserLoginView',
    //懒加载
    component: () => import('../views/UserLoginView.vue')
  },
  {
    path: '/page/user/center',
    name: 'UserCenterView',
    //懒加载
    component: () => import('../views/UserCenterView.vue')
  },
  {
    path: '/page/user/realname',
    name: 'RealNameView',
    //懒加载
    component: () => import('../views/RealNameView.vue')
  },
  {
    path: '/page/user/pay',
    name: 'UserPayView',
    //懒加载
    component: () => import('../views/UserPayView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
