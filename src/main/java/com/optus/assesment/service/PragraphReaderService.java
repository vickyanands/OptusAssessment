package com.optus.assesment.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.stream.Stream;

public interface PragraphReaderService {
	Stream<String> getParagraphStream() throws URISyntaxException, IOException;
}
