import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/login/LoginView.vue'
import TestView from '../views/reception/test/index.vue'
import BackView from '../views/backstage/index.vue'
import store from '../store'
import { awaitGet } from '../tool/http.js'
import { ElMessage } from 'element-plus'
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
        {
          path: 'productDetail',
          component: () => import('../views/reception/productDetail/index.vue')
        },
        {
          path: 'orderDetail',
          component: () => import('../views/reception/orderDetail/index.vue')
        },
        {
          path: 'link',
          component: () => import('../views/reception/link/index.vue')
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
    },
    {
      path: '/back',
      name: 'back',
      component: BackView,
      children: [
        {
          path: '',
          component: () => import('../views/backstage/type/index.vue')
        },
        {
          path: 'type',
          component: () => import('../views/backstage/type/index.vue')
        },
        {
          path: 'product',
          component: () => import('../views/backstage/product/index.vue')
        },
      ]
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



// router.beforeEach(async (to, from, next) => {
//   if (to.path != '/') {
//     if (store.state.userInfo.auth == '') {
//       await awaitGet('/user/login/session').then(res => {
//         if (res.code == 200) {
//           store.commit("setUserInfo", res.data)
//         } else {
//           ElMessage.error(res.msg)
//           router.push('/')
//         }
//       })
//     }
//     if (to.path == '/home/link' && store.state.userInfo.auth != null && store.state.userInfo.auth < 1) {
//       ElMessage.error("权限不足")
//       router.push('/home')
//     }
//   }

//   next()//需要调用next()才能放行
// })











export default router
