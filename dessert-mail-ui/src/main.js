import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from '../src/store/index.js'
import { axios } from '../src/tool/http.js'
import { useRouter } from 'vue-router'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.use(store)
app.config.globalProperties.$axios = axios


for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}




app.mount('#app')

