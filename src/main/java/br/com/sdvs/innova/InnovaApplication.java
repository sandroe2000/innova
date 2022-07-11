package br.com.sdvs.innova;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.com.sdvs.innova.core.domain.Role;
import br.com.sdvs.innova.core.domain.User;
import br.com.sdvs.innova.core.service.UserService;
import br.com.sdvs.innova.pessoa.domain.Sexo;
import br.com.sdvs.innova.pessoa.domain.Status;
import br.com.sdvs.innova.pessoa.domain.TipoDePublico;
import br.com.sdvs.innova.pessoa.repository.SexoRepository;
import br.com.sdvs.innova.pessoa.repository.StatusRepository;
import br.com.sdvs.innova.pessoa.repository.TipoDePublicoRepository;

@SpringBootApplication
public class InnovaApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private SexoRepository sexoRepository;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private TipoDePublicoRepository tipoDePublicoRepository;

	//CREATE DATABASE innova;
	//CREATE USER 'innova'@'localhost' IDENTIFIED WITH mysql_native_password BY 'Inn*v@01';
	//GRANT ALL PRIVILEGES ON *.* TO 'innova'@'localhost';
	//FLUSH PRIVILEGES;

	public static void main(String[] args) {
		SpringApplication.run(InnovaApplication.class, args);
	}

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return factory -> factory.setContextPath("/api");
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("*");
			}
		};
	}

	@Override
	public void run(String... args) {

		Role role = userService.getRole("ROLE_USER");

		if(role != null){
			return;
		}

		userService.saveRole(new Role(null, "ROLE_USER"));
		userService.saveRole(new Role(null, "ROLE_MANAGER"));
		userService.saveRole(new Role(null, "ROLE_ADMIN"));
		userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

		userService.saveUser(
			new User(null, "MARIANE C ESPINDULA", "MARIANE", "Inn*v@01", new ArrayList<>())
		);

		userService.addRoleToUser("MARIANE", "ROLE_MANAGER");
		userService.addRoleToUser("MARIANE", "ROLE_ADMIN");
		userService.addRoleToUser("MARIANE", "ROLE_SUPER_ADMIN");

		Sexo sexo = new Sexo();
		sexo.setCorporativo(null);
		sexo.setDescricao("FEMININO");
		sexo.setInativo(false);
		sexoRepository.save(sexo);

		sexo = new Sexo();
		sexo.setCorporativo(null);
		sexo.setDescricao("MASCULINO");
		sexo.setInativo(false);
		sexoRepository.save(sexo);

		Status status = new Status();
		status.setCorporativo(null);
		status.setDescricao("ATIVO");
		status.setInativo(false);
		statusRepository.save(status);

		status = new Status();
		status.setCorporativo(null);
		status.setDescricao("CANCELADO");
		status.setInativo(false);
		statusRepository.save(status);

		TipoDePublico tipoDePublico = new TipoDePublico();
		tipoDePublico.setCorporativo(null);
		tipoDePublico.setDescricao("CLIENTE COMUM");
		tipoDePublico.setInativo(false);
		tipoDePublicoRepository.save(tipoDePublico);

		tipoDePublico = new TipoDePublico();
		tipoDePublico.setCorporativo(null);
		tipoDePublico.setDescricao("LIMINAR JUD.C/CARTA");
		tipoDePublico.setInativo(false);
		tipoDePublicoRepository.save(tipoDePublico);

		tipoDePublico = new TipoDePublico();
		tipoDePublico.setCorporativo(null);
		tipoDePublico.setDescricao("SEGURADO VITALICIO");
		tipoDePublico.setInativo(false);
		tipoDePublicoRepository.save(tipoDePublico);

		tipoDePublico = new TipoDePublico();
		tipoDePublico.setCorporativo(null);
		tipoDePublico.setDescricao("LIMINAR JUDICIAL");
		tipoDePublico.setInativo(false);
		tipoDePublicoRepository.save(tipoDePublico);

	}
}