package graduationProject.IMUISystem.eventHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EventHandlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventHandlerApplication.class, args);
	}

	@Bean
	public RestTemplate createRestTemplateBean(){
		return new RestTemplate();
	}
	@Bean
	public ObjectMapper createObjectMapperBean(){
		return new ObjectMapper();
	}
}
