package com.ai.gcp.gcfunctions.cloudrun.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.ai.gcp.gcfunctions.cloudrun.repository.MySQLRepository;

public class UnifiedGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JTextArea outputArea;
	private MySQLRepository repository;

	public UnifiedGUI() {
		setTitle("AI + GCP Cloud Functions & Cloud Run Viewer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		repository = new MySQLRepository();
		outputArea = new JTextArea();
		outputArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(outputArea);

		JButton loadFunctionsButton = new JButton("Load Cloud Functions");
		JButton loadServicesButton = new JButton("Load Cloud Run Services");
		JButton showAnomalyChartButton = new JButton("Show Anomaly Chart");

		loadFunctionsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> functions = repository.getAllFunctions();
				outputArea.setText(String.join("\n", functions));
			}
		});

		loadServicesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> services = repository.getAllCloudRunServices();
				outputArea.setText(String.join("\n", services));
			}
		});

		showAnomalyChartButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChartPanelBuilder.showAnomalyChart();
			}
		});

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(loadFunctionsButton);
		buttonPanel.add(loadServicesButton);
		buttonPanel.add(showAnomalyChartButton);

		getContentPane().add(buttonPanel, BorderLayout.NORTH);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new UnifiedGUI().setVisible(true);
			}
		});
	}
}
