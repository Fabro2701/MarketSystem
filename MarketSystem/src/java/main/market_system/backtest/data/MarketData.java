package market_system.backtest.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;


public class MarketData extends ArrayList<CandleData>{
	Map<String,ArrayList<Double>>indicators;
	ArrayList<LocalDateTime>dates;
	
	private MarketData() {
		super();
		indicators = new HashMap<>();
		dates = new ArrayList<>();
	}
	public MarketData(String filename) {
		this(filename, defaultProperties);
	}
	public MarketData(String filename, Properties props) {
		super(Integer.valueOf(props.getProperty("capacity")));
		Objects.requireNonNull(filename, "null data filename");
		
		indicators = new HashMap<>();
		dates = new ArrayList<>(Integer.valueOf(props.getProperty("capacity")));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(props.getProperty("date_format"));
		
		String delimiter = props.getProperty("delimiter");
		
		File file = new File(filename);
		String[] values;
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16))) {
		    String line;
		    String header[] = br.readLine().split(delimiter);
		    int inds=header.length-5;
		    for(int i=0;i<inds;i++)this.indicators.put(header[i+5], new ArrayList<>());
		    
		    while ((line = br.readLine()) != null) {
		    	values = line.split(delimiter);
		    	
		    	//LocalDateTime date = LocalDateTime.parse(values[0].contains(":")?values[0]:values[0]+" 00:00:00", formatter);
		    	LocalDateTime date = LocalDateTime.parse(values[0], formatter);
		    	dates.add(date);
		    	
		    	CandleData cd = new CandleData(Double.parseDouble(values[1]), 
		    								   Double.parseDouble(values[2]), 
		    								   Double.parseDouble(values[3]), 
		    								   Double.parseDouble(values[4]));
		    	this.add(cd);
		    	
		    	for(int i=0;i<inds;i++) {
		    		this.indicators.get(header[5+i]).add(Double.parseDouble(values[5+i]));
		    	}
		    }
		    
		    System.out.println("File read - "+Arrays.toString(header));
		} catch (IOException e) {
			System.err.println("Error reading data from: "+filename);
			e.printStackTrace();
		}
	}
	
	
	static Properties defaultProperties;
	static {
		defaultProperties = new Properties();
		defaultProperties.put("delimiter", ",");
		defaultProperties.put("date_format", "yyyy.MM.dd HH:mm");
		defaultProperties.put("capacity", "0");
		
	}
	
	/**
	 * Fills map with the indicators at position i
	 * @param map
	 * @param i
	 */
	public void fillIndicators(Map<String, Double>map, int i) {
		for(Entry<String, ArrayList<Double>> e:this.indicators.entrySet()) {
			map.put(e.getKey(), e.getValue().get(i));
		}
	}
	public List<MarketData>split(int n){
		List<MarketData>ds = new ArrayList<>();
		
		int m = this.size()/n;
		for(int i=0;i<n;i++) {
			MarketData d = new MarketData();
			for(int j=m*i;j<m*(i+1);j++) {
				d.add(this.get(j));
				d.dates.add(this.dates.get(j));
				for(String k:this.indicators.keySet()) {
					d.indicators.computeIfAbsent(k, a->new ArrayList<>()).add(this.indicators.get(k).get(j));
				}
			}	
			ds.add(d);
		}
		
		return ds;
	}
	
	public LocalDateTime getDate(int i) {
		return this.dates.get(i);
	}
	
	
	public static void main(String arg[]) {
		MarketData m = new MarketData("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\data\\EURUSD-PERIOD_H1.csv");
		List<MarketData> ds = m.split(4);
		/*
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Duration duration = Duration.between(LocalDateTime.parse("2010-01-01 15:30:00", formatter), 
											 LocalDateTime.parse("2011-01-02 16:31:00", formatter));
		System.out.println(duration.toMinutes());
		*/
		//LocalDateTime date = LocalDateTime.parse("2010-01-01", formatter);
		/*String str = "2023-06-17 15:30:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        System.out.println(dateTime);*/
	}
}
