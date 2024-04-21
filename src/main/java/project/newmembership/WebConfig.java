package project.newmembership;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.newmembership.argumentresolver.LoginMemberArgumentResolver;
import project.newmembership.interceptor.LogInterceptor;
import project.newmembership.interceptor.LoginCheckInterceptor;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error");
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**","/*")
                .excludePathPatterns("/","/api/user/list", "/api/user", "/api/user/join","/logout"
                        , "/api/user/login", "/api/user/loginTry", "/css/**", "/*.ico", "/error"
                );

    }
}
