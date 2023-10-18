package gradutionProject.loginSystem.groupManager;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GroupManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroupManagerApplication.class, args);
	}
	@Bean
	public RestTemplate createRestTemplate(){
		return new RestTemplate();
	}
	@Bean
	public ObjectMapper createObjectMapper(){
		return new ObjectMapper();
	}
}
