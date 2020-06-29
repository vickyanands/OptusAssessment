package com.optus.assesment.integration;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.optus.assesment.CounterApiApplication;
import com.optus.assesment.dto.CountDto;
import com.optus.assesment.dto.SearchDto;

@SpringBootTest(classes = CounterApiApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
		public class TestIntegrations {
    @LocalServerPort
    private int port;
 
    URI base;
    @Autowired 
    private TestRestTemplate restTemplate;
	 
	    @BeforeEach
	    public void setUp() throws MalformedURLException, URISyntaxException {
	        restTemplate = new TestRestTemplate("optus", "candidates");
	        final String baseUrl = "http://localhost:"+port+"/counter-api/";
	        base = new URI(baseUrl);
	    }
	    
	    
	    @Test
	    public void whenLoggedUserRequestPostUrl_WithActualSearchData_ThenSuccess()
	     throws IllegalStateException, IOException {
	    	
	    	SearchDto searchDto = new SearchDto();
	    	List<String> listOfSearchStr = Arrays.asList("Duis","Sed","Pellentesque","123",".");    	
	    	searchDto.setSearchText(listOfSearchStr);
	    	System.out.println("base url "+ base );
	    	String postSearchMessagUrl = base.toString().concat("search/");
	        ResponseEntity<CountDto> response 
	          = restTemplate.postForEntity(postSearchMessagUrl, searchDto, CountDto.class);
	        System.out.println("Response received from Server"+ response);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals(response.getBody().getCounts().size(), 5);
	        assertEquals(response.getBody().getCounts().get(0).containsKey("Duis"),true );
	        assertEquals(response.getBody().getCounts().get(0).get("Duis").toString(),"11" );
	        assertEquals(response.getBody().getCounts().get(1).containsKey("Sed"),true );
	        assertEquals(response.getBody().getCounts().get(1).get("Sed").toString(),"16" );
	        assertEquals(response.getBody().getCounts().get(2).containsKey("Pellentesque"),true );
	        assertEquals(response.getBody().getCounts().get(2).get("Pellentesque").toString(),"6" );
	        assertEquals(response.getBody().getCounts().get(3).containsKey("123"),true );
	        assertEquals(response.getBody().getCounts().get(3).get("123").toString(),"0" );
	    }
	    
	    @Test
	    public void whenLoggedUserRequestGetUrl_WithTopNumberOfCounts_ThenSuccess()
	     throws IllegalStateException, IOException {
	    	
	    	String getTopResultUrl = base.toString().concat("top/2");
	        ResponseEntity<String> response 
	          = restTemplate.getForEntity(getTopResultUrl, String.class);
	  
	        System.out.println("When Logged User Requests "+ response.getBody());
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertThat(response).isNotNull();
	        assertThat(response.getBody()).containsIgnoringCase("vel|17\neget|17");
	    }
	    
	    @Test
	    public void whenLoggedUserRequestGetUrl_WithNullNumberOfCounts_ThenFailWithNotfound()
	     throws IllegalStateException, IOException {
	    	
	    	String getTopResultUrl = base.toString().concat("top/");
	        ResponseEntity<String> response 
	          = restTemplate.getForEntity(getTopResultUrl, String.class);
	  
	        System.out.println("When Logged User Requests "+ response.getBody());
	        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	        assertThat(response).isNotNull();
	    }
	    
	    @Test
	    public void whenLoggedUserRequestGetUrl_WithStringTestInsteadOfNumber_ThenSuccessWithInvalidUrlRequest()
	     throws IllegalStateException, IOException {
	    	
	    	String getTopResultUrl = base.toString().concat("top/oo");
	        ResponseEntity<String> response 
	          = restTemplate.getForEntity(getTopResultUrl, String.class);
	  
	        System.out.println("When Logged User Requests "+ response.getBody());
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertThat(response).isNotNull();
	        assertThat(response.getBody()).contains("invalid url please use /counter-api/top/{number}");
	    }
	    
	    @Test
	    public void whenLoggedUserRequestGetUrl_WithTop10_ThenSuccess()
	     throws IllegalStateException, IOException {
	    	
	    	String getTopResultUrl = base.toString().concat("top/10");
	        ResponseEntity<String> response 
	          = restTemplate.getForEntity(getTopResultUrl, String.class);
	  
	        System.out.println("Response for Request  "+ response.getBody());
	        System.out.println("First Response for top10  "+ response.getBody().split("\\r?\\n")[0]);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertThat(response).isNotNull();
	        assertThat(response.getBody().split("\\r?\\n").length).isEqualTo(10);
	    }
	    
	    @Test
	    public void whenLoggedUserRequestGetUrl_WithTop200_WithTotalExisting176_ThenSuccess()
	     throws IllegalStateException, IOException {
	    	
	    	String getTopResultUrl = base.toString().concat("top/200");
	        ResponseEntity<String> response 
	          = restTemplate.getForEntity(getTopResultUrl, String.class);
	  
	        System.out.println("Response for Request  "+ response.getBody());
	        System.out.println("Response count for top 200  "+ response.getBody().split("\\r?\\n").length);
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertThat(response).isNotNull();
	        assertThat(response.getBody().split("\\r?\\n").length).isLessThanOrEqualTo(200);
	    }
	    @Test
	    public void whenLoggedUserRequestGetUrl_WithTop0_ThenSuccess_BodyNull()
	     throws IllegalStateException, IOException {
	    	
	    	String getTopResultUrl = base.toString().concat("top/0");
	        ResponseEntity<String> response 
	          = restTemplate.getForEntity(getTopResultUrl, String.class);
	  
	        System.out.println("Response for Request  "+ response.getBody());
//	        System.out.println("Response count for top 0  "+ response.getBody().length());
	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertThat(response).isNotNull();
	        assertThat(response.getBody()).isNull();
	    } 
	    
	    @Test
		    public void whenLoggedUserRequestGetUrl_WithTop5_ThenSuccess_BodyNull()
		   	     throws IllegalStateException, IOException {
		   	    	
		   	    	String getTopResultUrl = base.toString().concat("top/5");
		   	        ResponseEntity<String> response 
		   	          = restTemplate.getForEntity(getTopResultUrl, String.class);
		   	  
		   	        System.out.println("Response for Request 5 "+ response.getBody());
		   	        assertEquals(HttpStatus.OK, response.getStatusCode());
		   	        assertThat(response).isNotNull();
			        assertThat(response.getBody().split("\\r?\\n").length).isLessThanOrEqualTo(5);
			        assertThat(Arrays.asList(response.getBody().split("\\r?\\n")))
			        .containsExactly("vel|17","eget|17","sed|16","in|15","et|14");
		   	    }
}
