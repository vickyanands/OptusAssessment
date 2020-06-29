package com.optus.assesment.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import com.optus.assesment.service.PragraphReaderService;
import com.optus.assesment.service.impl.PragraphReaderServiceImpl;

public class TestParagraphReaderService {
	 	 
    private PragraphReaderService pragraphReaderService = new PragraphReaderServiceImpl();
	 
	@Test
	public void givenFilePath_whenUsingFilesLines_thenFileData() {
//	    String expectedData = "Hello, world!";
	          
	    Stream<String> streamforFile = null;
		try {
			streamforFile = pragraphReaderService.getParagraphStream();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	          System.out.println(streamforFile);
	          
	    assertThat(streamforFile).isNotNull();
	}
}
