import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/login/LoginView.vue'
import TestView from '../views/reception/test/index.vue'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/home',
      name: 'home',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/reception/home/index.vue'),
      children: [
        {
          path: '',
          component: () => import('../views/reception/product/index.vue')
        },
        {
          path: 'userInfo',
          component: () => import('../views/reception/userInfo/index.vue')
        },
        {
          path: 'cart',
          component: () => import('../views/reception/cart/index.vue')
        },
        {
          path: 'order',
          component: () => import('../views/reception/order/index.vue')
        },
        {
          path: 'collection',
          component: () => import('../views/reception/collection/index.vue')
        },
        {
          path: 'settlement',
          component: () => import('../views/reception/settlement/index.vue')
        },
      
      ]
    },
    {
      path: '/',
      name: 'login',
      component: LoginView
    },
    {
      path: '/test',
      component: TestView
    }
  ]
})
// router.beforeEach((to, from, next) => {
//   if (to.path.startsWith('/Login/Login')) {
//       window.sessionStorage.removeItem('Token')
//       next()
//   } else {
//       let user = window.sessionStorage.getItem('Token')
//       if (!user) {
//           next({
//               path: '/Login/Login'
//           })
//       } else {
//           next()
//       }
//   }
// });
export default router
