package fr.eni.tp.filmotheque.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter.Directive;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		HeaderWriterLogoutHandler clearSiteData = new HeaderWriterLogoutHandler(new ClearSiteDataHeaderWriter(Directive.ALL));
				    
		http.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/img/**").permitAll()
				.requestMatchers("/films/creer").hasAnyRole("ADMIN")
				.anyRequest().authenticated())
				
		.httpBasic(Customizer.withDefaults())
		//.formLogin(Customizer.withDefaults());
		//personnalise la connexion
		.formLogin(form -> form
				.loginPage("/login")
				.permitAll())
		.logout(logout -> logout
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) //definit l'url permettant de se déconnecter
				.addLogoutHandler(clearSiteData) //
				.permitAll()
				);
		return http.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
	    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

	    // Requête pour récupérer l'utilisateur
	    jdbcUserDetailsManager.setUsersByUsernameQuery(
	        "SELECT email AS username, password, " +
	        "1 AS enabled " +
	        "FROM MEMBRE WHERE email = ?"
	    );

	    // Requête pour récupérer les rôles
	    jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
	        "SELECT email AS username, " +
	        "CASE WHEN admin = 1 THEN 'ROLE_ADMIN' ELSE 'ROLE_MEMBRE' END AS role " +
	        "FROM MEMBRE WHERE email = ?"
	    );

	    return jdbcUserDetailsManager;
	}
}
