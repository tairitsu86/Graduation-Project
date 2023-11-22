package graduationProject.loginSystem.userDatabase.integrationTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


//@ExtendWith(SpringExtension.class)
//@WebAppConfiguration
public class UserDatabaseControllerIntegrationTest {
//    @Autowired
//    private WebApplicationContext webApplicationContext;
//
//    private MockMvc mockMvc;
//
//    private static final String CONTENT_TYPE = "application/json";
//
//    @BeforeEach
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
//    }
//    @Test
//    public void givenWac_whenServletContext_thenItProvidesGreetController() {
//        final ServletContext servletContext = webApplicationContext.getServletContext();
//        assertNotNull(servletContext);
//        assertTrue(servletContext instanceof MockServletContext);
//        assertNotNull(webApplicationContext.getBean("userDatabaseController"));
//    }
//
//    @Test
//    public void userLogin_happy_path(){
//
//    }




}
