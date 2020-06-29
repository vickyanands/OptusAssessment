package com.optus.assesment.dto;

import java.util.List;
import java.util.Map;


public class CountDto {
	private List<Map<String,Long>>counts;

	public List<Map<String,Long>> getCounts() {
		return counts;
	}

	public void setCounts(List<Map<String,Long>> counts) {
		this.counts = counts;
	}
}
