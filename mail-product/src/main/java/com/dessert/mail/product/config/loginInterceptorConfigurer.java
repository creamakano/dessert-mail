// package com.dessert.mail.product.config;
//
// import com.dessert.mail.product.interceptor.LoginInterceptor;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.servlet.HandlerInterceptor;
// import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
// import java.util.ArrayList;
// import java.util.List;
//
// //处理器拦截器的注册
// @Configuration//加载当前的拦截器并进行注册
// public class loginInterceptorConfigurer implements WebMvcConfigurer {
//
//     //配置拦截器
//     @Override
//     public void addInterceptors(InterceptorRegistry registry) {
//         //创建自定义的拦截器对象
//         HandlerInterceptor interceptor=new LoginInterceptor();
//         //配置白名单：存放在一个List集合
//         List<String> patterns=new ArrayList<>();
//         patterns.add("/bootstrap3/**");
//         patterns.add("/css/**");
//         patterns.add("/images/**");
//         patterns.add("/js/**");
//         //完成拦截器的注册
//         registry.addInterceptor(interceptor)
//                 .addPathPatterns("/**")//表示拦截的url是什么
//                 .excludePathPatterns(patterns);//要放行的url
//     }
// }
