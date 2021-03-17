package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScans({
	@ComponentScan("ftz"),
	@ComponentScan("shared")
})
public class FtzApplication{

	public static void main(String[] args) {
		SpringApplication.run(FtzApplication.class, args);
	}
}
