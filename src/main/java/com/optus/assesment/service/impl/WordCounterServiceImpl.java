package com.optus.assesment.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.optus.assesment.dto.SearchDto;
import com.optus.assesment.service.PragraphReaderService;
import com.optus.assesment.service.WordCounterService;
@Service("wordCounterService")
@Lazy
public class WordCounterServiceImpl implements WordCounterService{
	private static final Logger log = LoggerFactory.getLogger(WordCounterServiceImpl.class);
	
	
	private final PragraphReaderService paraReaderSer;
	
	@Autowired
	public WordCounterServiceImpl(PragraphReaderService parReaderSer){
		this.paraReaderSer = parReaderSer;
	}
	
	@Override
	public List<String> getTopWordCountOrderedDesc() {
		try {
			Optional<Stream<String>> content = Optional.ofNullable(paraReaderSer.getParagraphStream());
		Map<String, Long> descWordCountMap = content.isPresent()?
						content.get()
						.parallel()
						.flatMap(line -> Arrays.stream(line.trim().split(" ")))
						.map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
							.filter(word -> !word.isEmpty())
						.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
						.entrySet().stream()
						.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
						.collect(Collectors.toMap(
								Map.Entry::getKey, Map.Entry::getValue,
								(oldValue, newValue) -> oldValue, LinkedHashMap::new)):Collections.emptyMap();

		List<String> listOfTopSortedValues = descWordCountMap.entrySet().stream()
				.map(e -> String.format("%s|%s", e.getKey(), String.valueOf(e.getValue())))
				.collect(Collectors.toList());
		return listOfTopSortedValues;
		} catch (URISyntaxException | IOException e1) {
			e1.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public List<Map<String, Long>> getWordCountMapForSearchQueries(SearchDto searchDto)
			throws URISyntaxException, IOException {

		Stream<String> content = paraReaderSer.getParagraphStream();

		Map<String, Long> countOfEntriesIgnoreingCase = content.flatMap(line -> Arrays.stream(line.trim().split(" ")))
				.map(word -> word.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase().trim()).filter(word -> !word.isEmpty())
				.filter(word -> searchDto.getSearchText().stream().map(e -> e.toLowerCase())
						.collect(Collectors.toList()).indexOf(word.toLowerCase()) != -1)
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		log.info(countOfEntriesIgnoreingCase.toString());
		List<Map<String, Long>> wordCounts = searchDto.getSearchText().stream().map(searchText -> {
			Map<String, Long> lmap = new HashMap<String, Long>();
			lmap.put(searchText, countOfEntriesIgnoreingCase.getOrDefault(searchText.toLowerCase(), 0l));
			return lmap;
		}).collect(Collectors.toList());
		return wordCounts;
	
	}

}
