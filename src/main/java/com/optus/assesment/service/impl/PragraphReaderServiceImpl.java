package com.optus.assesment.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.optus.assesment.service.PragraphReaderService;

@Service("pragraphReaderService")
@Lazy
public class PragraphReaderServiceImpl implements PragraphReaderService {

	private static final Logger log = LoggerFactory.getLogger(PragraphReaderServiceImpl.class);

	@Value("${file.path:}") 
	String fileAbsolutePath;
	@Override
	public Stream<String> getParagraphStream() throws URISyntaxException, IOException {
				
				log.info("fileAbsolutePath from Application properties",fileAbsolutePath);
				Path path; 
				path =  fileAbsolutePath!=null &&!fileAbsolutePath.isEmpty()?Paths.get(fileAbsolutePath):Paths.get(getClass().getClassLoader()
			      .getResource("fileTest.txt").toURI());
				log.info("file path Selected "+path);
				
		return Files.lines(path);
	}

}
