package com.ai.gcp.gcfunctions.cloudrun.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CloudRunRepository {

	private static final String API_URL = "http://127.0.0.1:5000/gcp/cloudrun";

	public List<String> fetchServices() {
		List<String> list = new ArrayList<>();
		try {
			URL url = new URL(API_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null)
				response.append(inputLine);
			in.close();

			list.add(response.toString());
		} catch (Exception e) {
			list.add("Error: " + e.getMessage());
		}
		return list;
	}
}
