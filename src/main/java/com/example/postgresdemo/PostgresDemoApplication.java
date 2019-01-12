package com.example.postgresdemo;

import com.example.postgresdemo.model.Role;
import com.example.postgresdemo.model.User;
import com.example.postgresdemo.repository.UserRepository;
import com.example.postgresdemo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;


@SpringBootApplication
@EnableJpaAuditing
public class PostgresDemoApplication {


    @Bean
    public CommandLineRunner setupDefaultUser(UserService service) {
        return args -> {
            try {
                service.save(new User(
                        "user8", //username
                        "user8", //password
                        Arrays.asList(new Role("USER"), new Role("ACTUATOR")),//roles
                        true//Active
                ));
            }
            catch(Exception ex){

            }
        };
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        SpringApplication.run(PostgresDemoApplication.class, args);
    }

}
