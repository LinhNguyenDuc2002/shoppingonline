package com.example.shoppingonline.config;

import com.example.shoppingonline.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.Collections;

@Configuration //đánh dấu lớp cấu hình
@EnableWebSecurity
public class WebSecurityConfig{
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){ //mã hóa pass
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new ProviderManager(Collections.singletonList(authenticationProvider()));
    }
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { //phân quyền
        http.csrf().disable()//chống tấn công xác thực
                .authorizeRequests()//phân quyền request
                .requestMatchers("/product", "/blueshop/infor", "/blueshop/update", "/blueshop/delete",
                        "/product/addproduct","/product/edit/**","/product/update/**").hasAuthority("ROLE_USER")
                .requestMatchers("/blueshop/info").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll() // với endpoint /customer/** sẽ yêu cầu authenticate
                .and()
                .formLogin() // trả về page login nếu chưa authenticate
//                .loginPage("/login") // URL của form đăng nhập tự tạo
                .loginProcessingUrl("/login") // URL để xử lý đăng nhập
                .usernameParameter("username") // Tên tham số của username từ form login tự tạo
                .passwordParameter("password") // Tên tham số của password từ form login tự tạo
                .defaultSuccessUrl("/blueshop",true) // URL chuyển hướng sau khi đăng nhập thành công
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .logout()
                .logoutUrl("blueshop/logout")
                .logoutSuccessUrl("/blueshop")
                .permitAll();
        return http.build();
    }
}