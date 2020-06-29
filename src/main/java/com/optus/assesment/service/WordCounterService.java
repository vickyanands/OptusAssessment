package com.optus.assesment.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import com.optus.assesment.dto.SearchDto;

public interface WordCounterService {
	List<String> getTopWordCountOrderedDesc();
	List<Map<String, Long>> getWordCountMapForSearchQueries(SearchDto searchDto)
			throws URISyntaxException, IOException;
}
