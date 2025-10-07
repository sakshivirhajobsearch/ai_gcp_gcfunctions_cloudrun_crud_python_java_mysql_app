package com.ai.gcp.gcfunctions.cloudrun.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLRepository {
	
	private static final String URL = "jdbc:mysql://localhost:3306/ai_gcp_gcfunctions_cloudrun";
	private static final String USER = "root";
	private static final String PASSWORD = "admin";

	private Connection connect() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

	public List<String> getAllFunctions() {
		List<String> functions = new ArrayList<>();
		String sql = "SELECT name, runtime, entry_point, url, is_anomaly FROM gcp_functions ORDER BY created_at DESC";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Function: ").append(rs.getString("name")).append(" | ");
				sb.append("Runtime: ").append(rs.getString("runtime")).append(" | ");
				sb.append("Entry: ").append(rs.getString("entry_point")).append(" | ");
				sb.append("URL: ").append(rs.getString("url")).append(" | ");
				sb.append("Anomaly: ").append(rs.getBoolean("is_anomaly"));
				functions.add(sb.toString());
			}
		} catch (SQLException e) {
			functions.add("Error: " + e.getMessage());
		}
		return functions;
	}

	public List<String> getAllCloudRunServices() {
		List<String> services = new ArrayList<>();
		String sql = "SELECT name, uri, template, is_anomaly FROM gcp_cloudrun_services ORDER BY created_at DESC";
		try (Connection conn = connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			while (rs.next()) {
				StringBuilder sb = new StringBuilder();
				sb.append("Service: ").append(rs.getString("name")).append(" | ");
				sb.append("URI: ").append(rs.getString("uri")).append(" | ");
				sb.append("Template: ").append(rs.getString("template")).append(" | ");
				sb.append("Anomaly: ").append(rs.getBoolean("is_anomaly"));
				services.add(sb.toString());
			}
		} catch (SQLException e) {
			services.add("Error: " + e.getMessage());
		}
		return services;
	}
}