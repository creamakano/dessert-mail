import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from '../src/store/index.js'
import { axios } from '../src/tool/http.js'
import { useRouter } from 'vue-router'
const app = createApp(App)
app.use(router)
app.use(ElementPlus)
app.use(store)
app.config.globalProperties.$axios = axios







app.mount('#app')

