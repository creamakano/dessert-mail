// package com.dessert.mail.product.interceptor;
//
// import org.springframework.web.servlet.HandlerInterceptor;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
//
// //定义一个拦截器
// public class LoginInterceptor implements HandlerInterceptor {
//     /**
//      * 检测全局session对象中是否有uid数据，如果有则放行
//      * 没有重定向到登录界面
//      *
//      * @param request  请求对象
//      * @param response 响应对象
//      * @param handler  处理器
//      * @return true放行当前请求，false拦截当前请求
//      * @throws Exception
//      */
//     @Override
//     public boolean preHandle(HttpServletRequest request,
//                              HttpServletResponse response,
//                              Object handler) throws Exception {
//         //HttpServleRequest对象来获取session对象
//         Object obj = request.getSession().getAttribute("LoginUser");
//         if (obj == null) {//说明用户没有登录过系统，则重定向到登录界面
//             // response.s
//             response.setHeader("success","false");
//             //结束后续的调用
//             return false;
//         }
//         response.setHeader("success","true");
//         //请求放行
//         return true;
//     }
// }