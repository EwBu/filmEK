package pl.kowalska.filmek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("pl.kowalska.filmek.repository")
@SpringBootApplication
public class FilmEkApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilmEkApplication.class, args);


    }


}
