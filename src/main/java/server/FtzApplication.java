package server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
        @ComponentScan("ftz"),
        @ComponentScan("shared")
})
public class FtzApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(FtzApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
