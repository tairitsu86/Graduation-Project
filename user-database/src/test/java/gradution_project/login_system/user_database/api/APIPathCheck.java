package gradution_project.login_system.user_database.api;

import gradution_project.login_system.user_database.api.dto.UserLoginDto;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class APIPathCheck {

    final static String URL = "http://localhost:8080";

    @Test
    @Tag("APIPathCheck")
    public void givenUserDoesNotExists_whenUserInfoIsRetrieved_then404IsReceived() throws IOException {

        // Given
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(RandomStringUtils.randomAlphabetic( 8 ));
        userLoginDto.setPassword(RandomStringUtils.randomAlphabetic( 8 ));
        userLoginDto.setKeepLogin(false);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP entity with the JSON object as the request body and headers
        HttpEntity<UserLoginDto> requestEntity = new HttpEntity<>(userLoginDto, headers);

        // Send the POST request
        RestTemplate restTemplate = new RestTemplate();


        // When
        ResponseEntity<Void> response = restTemplate.postForEntity(URL+"/login", requestEntity,Void.class);

        // Then
        assertThat(
                response.getStatusCode(),
                equalTo(HttpStatus.SC_NOT_FOUND)
        );
    }

}
