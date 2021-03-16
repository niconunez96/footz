package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("ftz")
public class FtzApplication{

	public static void main(String[] args) {
		SpringApplication.run(FtzApplication.class, args);
	}
}
