package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sk.tuke.gamestudio.game.numberlink.consoleui.ConsoleUI;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
@Configuration
public class SpringClient {

    public static void main(String[] args) {
        SpringApplication.run(SpringClient.class, args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI console){
        return s->console.play();
    }

    @Bean
    public ConsoleUI console(){
        return new ConsoleUI();
    }

    @Bean
    public ScoreService scoreService(){
        return new ScoreServiceJPA();
    }
    @Bean
    public CommentService commentService() {
        return new CommentServiceJDBC();
    }
}
