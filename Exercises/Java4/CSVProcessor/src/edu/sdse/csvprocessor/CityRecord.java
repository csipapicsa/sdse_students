package edu.sdse.csvprocessor;

public class CityRecord {
	private int id;
	private int year;
	private String city;
	private int population;
	
	public CityRecord(int id, int year, String city, int population) {
		this.id = id;
		this.year = year;
		this.population = population;
		this.city = city;
	}	
	
	public int getId() {
		return id;
	}
	public int getYear() {
		return year;
	}
	public String getCity() {
		return city;
	}
	public int getPopulation() {
		return population;
	}
	/* override just a safety thing, it keeps the original toString class method
	 * and just using my ones*/
	public String toString() {
		return "id: " + id + ", year: " + year + ", city: " + city + ", population: " + population;
	}

}
