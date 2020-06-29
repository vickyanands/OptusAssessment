package com.optus.assesment.security.config;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.optus.assesment.CounterApiApplication;
import com.optus.assesment.dto.SearchDto;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CounterApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)

public class TestWebSecurityConfiguration {
 
    TestRestTemplate restTemplate;
    URL base;
    @LocalServerPort int port;
 
    @BeforeEach
    public void setUp() throws MalformedURLException {
        restTemplate = new TestRestTemplate("optus", "candidates");
        base = new URL("http://localhost:" + port);
    }
 
    @Test
    public void whenLoggedUserRequestPostUrl_WithNullSearchData_ThenSuccess()
     throws IllegalStateException, IOException {
    	
    	SearchDto serachDto = new SearchDto();
    	String postSearchMessagUrl = base.toString().concat("/counter-api/search/");
        ResponseEntity<SearchDto> response 
          = restTemplate.postForEntity(postSearchMessagUrl, serachDto, SearchDto.class);
  
        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertTrue(response
//          .getBody()
//          .contains("optus"));
    }
 
    
    @Test
    public void whenUserWithWrongCredentials_thenUnauthorizedPage() 
      throws Exception {
  
        restTemplate = new TestRestTemplate("user", "wrongpassword");
        ResponseEntity<String> response 
          = restTemplate.getForEntity(base.toString(), String.class);
  
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
//        assertTrue(response
//          .getBody());
    }
}