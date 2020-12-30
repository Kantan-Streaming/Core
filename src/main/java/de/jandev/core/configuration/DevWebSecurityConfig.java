package de.jandev.core.configuration;

import de.jandev.core.security.JwtTokenFilterConfigurer;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Profile("dev")
@Configuration
@RestController
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class DevWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenFilterConfigurer jwtTokenFilterConfigurer;

    public DevWebSecurityConfig(JwtTokenFilterConfigurer jwtTokenFilterConfigurer) {
        this.jwtTokenFilterConfigurer = jwtTokenFilterConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/callback").permitAll()
                .antMatchers("/v2/api-docs", "/swagger-resources", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
                .anyRequest().authenticated();

        http.apply(jwtTokenFilterConfigurer);
    }

    @Bean
    public FilterRegistrationBean customCorsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
