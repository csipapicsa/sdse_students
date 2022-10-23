package edu.sdse.csvprocessor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.HashMap;

/*import edu.sdse.csvparser.ArrayList;
import edu.sdse.csvparser.CityRecord;
import edu.sdse.csvparser.HashMap;
import edu.sdse.csvparser.Map;
*/
public class CityCSVProcessor {
	private List<CityRecord> allRecords;
	private java.util.Map<String, List<CityRecord>> recordsByCity;
	
	public CityCSVProcessor() {
		allRecords = new ArrayList<>();
		recordsByCity = new HashMap<>();
	}
	
	public void readAndProcess(File file) {
		allRecords.clear();
		recordsByCity.clear();
		
		readCSVFile(file);
		processRecords();
	}
	
	
	
	public void readCSVFile(File file) {
		//Try with resource statement (as of Java 8)
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			//Discard header row
			br.readLine();
			
			String line;
			
			while ((line = br.readLine()) != null) {
				// Parse each line
				String[] rawValues = line.split(",");
				
				int id = convertToInt(rawValues[0]);
				int year = convertToInt(rawValues[1]);
				String city = convertToString(rawValues[2]);
				int population = convertToInt(rawValues[3]);
				
				CityRecord record = new CityRecord(id, year, city, population);
				allRecords.add(record);
				
				List<CityRecord> recordsOfCity = recordsByCity.get(city);
				
				// System.out.println("id: " + id + ", year: " + year + ", city: " + city + ", population: " + population);
				
				//TODO: Extend the program to process entries!
				
				// if record list doesnt exist
				if(recordsOfCity == null) {
					recordsOfCity = new	ArrayList<>();
					recordsByCity.put(city, recordsOfCity);
				}
				
				recordsOfCity.add(record);
			}
		} catch (Exception e) {
			System.err.println("An error occurred:");
			e.printStackTrace();
		}
	}
	
	private String cleanRawValue(String rawValue) {
		return rawValue.trim();
	}
	
	private int convertToInt(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		return Integer.parseInt(rawValue);
	}
	
	private String convertToString(String rawValue) {
		rawValue = cleanRawValue(rawValue);
		
		if (rawValue.startsWith("\"") && rawValue.endsWith("\"")) {
			return rawValue.substring(1, rawValue.length() - 1);
		}
		
		return rawValue;
	}
	
	private void processRecords() {
		for (Entry<String, List<CityRecord>> entry : recordsByCity.entrySet()) {
			String city = entry.getKey();
			List<CityRecord> recordsOfCity = entry.getValue();
			
			int numberOfEntries = recordsOfCity.size();
			
			// there are several ways to find minimum and maximum numbers, we just hardcode it here. Checck the video
			//int minYear = 0;
			//int maxYear = 0;
			
			
			//int minYear = Integer.MAX_VALUE;
			//int maxYear = Integer.MIN_VALUE;
			
			int minYear = 0;
			int maxYear = 0;
			
			long totalPopulation = 0;
			
			boolean isFirstRecord = true;
			
			for (CityRecord record : recordsOfCity) {
				int year = record.getYear();
				
				if (isFirstRecord) {
					minYear = year;
					maxYear = year;
					// it can come here 
					// isFirstRecord = false;
				}
				
				if (year < maxYear) {
					minYear = year;
				}
				
				if (year>maxYear) {
					maxYear = year;
				}
				
				int population = record.getPopulation();
				totalPopulation += population;
				
				isFirstRecord = false;
			}
			
			int averagePopulation = (int) (totalPopulation / numberOfEntries);
			
			String  message = "";
			
			message += "Average population of " + city + " ";
			message += "(" + minYear + "-" + maxYear + "; " + numberOfEntries + " entries): ";
			message += averagePopulation;
			
			System.out.println(message);
			
			
		}
	}
	
	
	
	public static final void main(String[] args) {
		CityCSVProcessor reader = new CityCSVProcessor();
		
		File dataDirectory = new File("data/");
		File csvFile = new File(dataDirectory, "Cities.csv");
		
		reader.readAndProcess(csvFile);
	}
}
