package graduationProject.IMUISystem.eventExecutor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EventExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventExecutorApplication.class, args);
	}

	@Bean
	public ObjectMapper createObjectMapper(){
		return new ObjectMapper();
	}
	@Bean
	public RestTemplate createRestTemplate(){
		return new RestTemplate();
	}
}
