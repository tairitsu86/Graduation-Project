package gradutionProject.IMUISystem.messageSender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MessageSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageSenderApplication.class, args);
	}
	@Bean
	public RestTemplate createRestTemplateBean(){
		return new RestTemplate();
	}
}
