package it.uniroma3.siw.authentication;

import it.uniroma3.siw.spring.model.CustomOAuth2User;
import it.uniroma3.siw.spring.service.CredentialsService;
import it.uniroma3.siw.spring.service.CustomOAuth2UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/* VARIABILE ADMIN */

import java.io.IOException;
import java.util.Map;

import static it.uniroma3.siw.spring.model.Credentials.RUOLO_ADMIN;
import static it.uniroma3.siw.spring.model.Credentials.RUOLO_DEFAULT;


@Configuration
//@EnableWebSecurity
@EnableOAuth2Client
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected CredentialsService credentialsService;
    /**
     * La variabile dataSource serve per accedere direttamente ai dati all'interno del DB.
     */
    @Autowired
    DataSource dataSource;

    @Autowired
    private CustomOAuth2UserService oauthUserService;
    /**
     * Il metodo gestisce le varie autorizzazioni che possono riguardare un utente normale (con ruolo DEFAULT)
     * e un super utente (con ruolo ADMIN). Gestisce inoltre il logout.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                //definiamo le pagine accessibili da chiunque
                .antMatchers(HttpMethod.GET, "/", "/index", "/chisiamo" ,"/login", "/register", "/css/**" ,"/images/**", "/oauth/**").permitAll()
                .antMatchers(HttpMethod.POST, "/login", "/register").permitAll()

                //definiamo le pagine accessibili solo dall'amministratore
                .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(RUOLO_ADMIN)
                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(RUOLO_ADMIN)

                //per visitare il resto delle pagine bisogna autenticarsi
                .anyRequest().authenticated()

                /*
                    da qui viene gestita l'autenticazione con OAuth o normalmente
                 */
                .and().oauth2Login().loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                        Authentication authentication) throws IOException, ServletException {

                        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

                        //viene creato un nuovo utente anche nel database
                        credentialsService.processOAuthPostLogin(oauthUser.getUsername(), oauthUser);
                        response.sendRedirect("/default");

                    }
                })
                .and()
                .formLogin()
                .loginPage("/login")

                // se il login ha successo, si viene rediretti al path /default
                .defaultSuccessUrl("/default")
                .failureUrl("/login?error")

                /*
                    si inizia a gestire il logout
                 */
                .and().logout()
                // il logout avviene attraverso questa riga, .logoutUrl non invalidava
                // la sessione correttamente
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // in caso di successo, si viene reindirizzati alla page iniziale
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true).permitAll();

    }

    /**
     * Questo metodo è necessario per ottenere dal database i dati degli utenti.
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(this.dataSource)
                //recuperiamo il ruolo
                .authoritiesByUsernameQuery("SELECT username, ruolo FROM credentials WHERE username=?")
                //recuperiamo le credenziali dell'utente
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    /**
     * Questo metodo si occupa di effettuare l'encrypt e il decrypt della password dell'utente salvata nel
     * database.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}