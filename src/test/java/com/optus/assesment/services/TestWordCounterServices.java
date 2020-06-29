package com.optus.assesment.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.optus.assesment.service.PragraphReaderService;
import com.optus.assesment.service.WordCounterService;
import com.optus.assesment.service.impl.WordCounterServiceImpl;

public class TestWordCounterServices {
	
	@Mock
	PragraphReaderService paragraphReaderSer;
	
	WordCounterService wordCounter;
	
	@BeforeEach()
	void beforeEach(){
		MockitoAnnotations.initMocks(this);
		wordCounter = new WordCounterServiceImpl(paragraphReaderSer);
	}
	    
    @Test
	public void getTopWordCountOrderedDescTest() throws URISyntaxException, IOException {
	    List<String> expected = new ArrayList<>();
	    expected.add("test|2");
	    expected.add("abc|1");
	    expected.add("string|1");
	    
    	
    	when(paragraphReaderSer.getParagraphStream()).thenReturn(Stream.of("Test test String ABC"));
    	List<String> result = wordCounter.getTopWordCountOrderedDesc();
    	System.out.println(result);
    	assertThat(result).containsAll(expected);
  
    	
    	
	}

}
