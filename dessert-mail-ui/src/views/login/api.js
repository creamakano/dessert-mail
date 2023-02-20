import get from '../../tool/http.js'
let msg1 = 0

// await get('/product/test').then(data => {
//   msg1 = data
// })
const msg = () => get('/product/test')
export { msg, msg1 }
