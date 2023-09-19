package gradutionProject.IoTSystem.deviceDatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class DeviceDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceDatabaseApplication.class, args);
	}
	@Bean
	public RestTemplate createRestTemplateBean(){
		return new RestTemplate();
	}
}
