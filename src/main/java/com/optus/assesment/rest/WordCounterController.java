package com.optus.assesment.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.optus.assesment.dto.CountDto;
import com.optus.assesment.dto.SearchDto;
import com.optus.assesment.service.WordCounterService;

@RestController
@RequestMapping(value = "/counter-api")
public class WordCounterController {

	private static final Logger log = LoggerFactory.getLogger(WordCounterController.class);

	@Autowired
	WordCounterService wordCountSer;

	@RequestMapping(value = "/search", method = RequestMethod.POST, consumes = "application/json")
	CountDto search(@RequestBody SearchDto searchDto) {
		
		CountDto counts = new CountDto();
		try{
		log.info("We are inside Search Method " + searchDto.getSearchText());
		List<Map<String, Long>> wordCounts = Optional.ofNullable(searchDto.getSearchText())
				.isPresent()?wordCountSer.getWordCountMapForSearchQueries(searchDto):Collections.emptyList();
		counts.setCounts(wordCounts);
		}catch (Exception e){
			log.error("Caught exception in service", e);
			counts.setCounts(Collections.emptyList());
		}
		return counts;
	}



	@RequestMapping(value = "/top/{param}", method = RequestMethod.GET, produces = "text/csv")
	String topWordCount(@PathVariable String param) throws URISyntaxException, IOException {
		try {
			
		log.info("We are inside top WordCount function with input param " + param);
		int topParameter = Optional.ofNullable(param)
								.map(Integer::parseInt).orElse(0);
		log.info("Param after processing ", topParameter);

		List<String> listOfTopSortedValues = topParameter>0?wordCountSer.getTopWordCountOrderedDesc():Collections.emptyList();
		log.info("test: befor sending response if it existings", listOfTopSortedValues.stream().limit(topParameter).collect(Collectors.joining("\n")));

		return listOfTopSortedValues.stream().limit(topParameter).collect(Collectors.joining("\n"));
		} catch (NumberFormatException e) {
			return "invalid url please use /counter-api/top/{number}";
		}
		}


	
}
