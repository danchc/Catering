package it.uniroma3.siw.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/* VARIABILE ADMIN */
import static it.uniroma3.siw.spring.model.Credentials.RUOLO_ADMIN;
import static it.uniroma3.siw.spring.model.Credentials.RUOLO_DEFAULT;


@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * La variabile dataSource serve per accedere direttamente ai dati all'interno del DB.
     */
    @Autowired
    DataSource dataSource;

    /**
     * Il metodo gestisce le varie autorizzazioni che possono riguardare un utente normale (con ruolo DEFAULT)
     * e un super utente (con ruolo ADMIN). Gestisce inoltre il logout.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http// authorization paragraph: qui definiamo chi può accedere a cosa
                .authorizeRequests()
                // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                .antMatchers(HttpMethod.GET, "/", "/index", "/login", "/register", "/css/**" ,"/images/**").permitAll()
                // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register
                .antMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
                .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(RUOLO_ADMIN)
                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(RUOLO_ADMIN)
                .antMatchers(HttpMethod.GET, "/buffets").hasAnyAuthority(RUOLO_DEFAULT, RUOLO_ADMIN)
                // tutti gli utenti autenticati possono accere alle pagine rimanenti
                .anyRequest().authenticated()

                // login paragraph: qui definiamo come è gestita l'autenticazione
                // usiamo il protocollo formlogin
                .and().formLogin()
                // la pagina di login si trova a /login
                // NOTA: Spring gestisce il post di login automaticamente
                .loginPage("/login")
                // se il login ha successo, si viene rediretti al path /default
                .defaultSuccessUrl("/default")
                .failureUrl("/login?error")

                // logout paragraph: qui definiamo il logout
                .and().logout()
                // il logout è attivato con una richiesta GET a "/logout"
                .logoutUrl("/logout")
                // in caso di successo, si viene reindirizzati alla /index page
                .logoutSuccessUrl("/index")
                .invalidateHttpSession(true)
                .clearAuthentication(true).permitAll();

    }

    /**
     * This method provides the SQL queries to get username and password.
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {


        auth.jdbcAuthentication()
                .dataSource(this.dataSource)
                //use the autowired datasource to access the saved credentials
                //retrieve username and role
                .authoritiesByUsernameQuery("SELECT username, ruolo FROM credentials WHERE username=?")
                //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
/*
        auth
                .inMemoryAuthentication()
                .withUser("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(RUOLO_ADMIN);*/

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