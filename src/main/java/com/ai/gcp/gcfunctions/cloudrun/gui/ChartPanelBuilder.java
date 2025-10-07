package com.ai.gcp.gcfunctions.cloudrun.gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartPanelBuilder {

	public static void showAnomalyChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		int[] functionStats = getAnomalyStats("gcp_functions");
		int[] serviceStats = getAnomalyStats("gcp_cloudrun_services");

		dataset.addValue(functionStats[0], "Normal", "Functions");
		dataset.addValue(functionStats[1], "Anomaly", "Functions");

		dataset.addValue(serviceStats[0], "Normal", "Cloud Run");
		dataset.addValue(serviceStats[1], "Anomaly", "Cloud Run");

		JFreeChart barChart = ChartFactory.createBarChart("AI Anomaly Detection (Cloud Functions & Cloud Run)",
				"Service Type", "Count", dataset);

		ChartPanel chartPanel = new ChartPanel(barChart);
		JFrame chartFrame = new JFrame("Anomaly Chart");
		chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		chartFrame.setSize(600, 400);
		chartFrame.add(chartPanel);
		chartFrame.setLocationRelativeTo(null);
		chartFrame.setVisible(true);
	}

	private static int[] getAnomalyStats(String tableName) {

		int normal = 0, anomaly = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ai_gcp_gcfunctions_cloudrun",
				"root", "admin");

				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(
						"SELECT is_anomaly, COUNT(*) AS count FROM " + tableName + " GROUP BY is_anomaly")) {
			while (rs.next()) {
				boolean isAnomaly = rs.getBoolean("is_anomaly");
				int count = rs.getInt("count");
				if (isAnomaly)
					anomaly += count;
				else
					normal += count;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return new int[] { normal, anomaly };
	}
}
