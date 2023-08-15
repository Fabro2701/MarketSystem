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

import model.Util.Pair;


public class MarketData {
	private CandleData candles[];
	private Map<String,double []>indicators;
	private LocalDateTime dates[];
	private int size;
	private MarketData() {
		
	}
	public MarketData(String filename) {
		this(filename, defaultProperties);
	}
	public MarketData(String filename, Properties props) {
		Objects.requireNonNull(filename, "null data filename");
		
		
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(props.getProperty("date_format"));
		
		String delimiter = props.getProperty("delimiter");
		
		File file = new File(filename);
		String[] values;
		
		int candleIndices = 7;
		try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_16))) {
		    int n = Integer.valueOf(br.readLine());
		    size = n;
		    indicators = new HashMap<>();
			dates = new LocalDateTime[n];
			candles = new CandleData[n];
			String line;
		    String header[] = br.readLine().split(delimiter);
		    int inds=header.length-candleIndices;
		    for(int i=0;i<inds;i++)this.indicators.put(header[i+candleIndices], new double[n]);
		    
		    int idx=0;
		    while ((line = br.readLine()) != null) {
		    	values = line.split(delimiter);
		    	
		    	//LocalDateTime date = LocalDateTime.parse(values[0].contains(":")?values[0]:values[0]+" 00:00:00", formatter);
		    	dates[idx] = LocalDateTime.parse(values[0], formatter);
		    	
		    	CandleData cd = new CandleData(Double.parseDouble(values[1]), 
		    								   Double.parseDouble(values[2]), 
		    								   Double.parseDouble(values[3]), 
		    								   Double.parseDouble(values[4]), 
		    								   Double.parseDouble(values[5]), 
		    								   Double.parseDouble(values[6]));
		    	candles[idx] = cd;
		    	
		    	for(int i=0;i<inds;i++) {
		    		this.indicators.get(header[candleIndices+i])[idx] = Double.parseDouble(values[candleIndices+i]);
		    	}
		    	idx++;
		    }
		    
		    System.out.println("File "+file.getName()+" read -"+n+"- "+Arrays.toString(header));
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
		int windowSize = 3;
		for(Entry<String, double[]> e:this.indicators.entrySet()) {
			for(int j=0;j<windowSize;j++) {
				map.put(e.getKey()+"_"+j, e.getValue()[i-(i>=j?j:0)]);
			}
		}
	}
	public List<MarketData>split(int n){
		
		double m = 1.0/n;
		double arr[] = new double[n];
		for(int i=0;i<n;i++)arr[i] = m;
		
		return this.split(arr);
	}
	public List<MarketData>split(double ...p){
		List<MarketData>ds = new ArrayList<>();
		
		int cursor=0;
		for(int i=0;i<p.length;i++) {
			int m = (int) (this.size()*p[i]);
			MarketData d = new MarketData();
			d.dates = Arrays.copyOfRange(this.dates, cursor, cursor+m);
			d.candles = Arrays.copyOfRange(this.candles, cursor, cursor+m);
			d.indicators = new HashMap<>();
			for(String k:this.indicators.keySet()) {
				d.indicators.put(k, Arrays.copyOfRange(this.indicators.get(k), cursor, cursor+m));
			}
			cursor += m;
			d.size = m;
			ds.add(d);
		}
		
		return ds;
	}
	public LocalDateTime getDate(int i) {
		return this.dates[i];
	}
	public CandleData getCandle(int i) {
		return this.candles[i];
	}
	public double[] getIndicator(String k) {
		return this.indicators.get(k);
	}
	public double getIndicator(String k, int i) {
		return this.indicators.get(k)[i];
	}
	public int size() {
		return this.size;
	}
	public CandleData[] getCandles() {
		return candles;
	}
	public Map<String, double[]> getIndicators() {
		return indicators;
	}
	public LocalDateTime[] getDates() {
		return dates;
	}
	
	
	public static void main(String arg[]) {
		MarketData m = new MarketData("C:\\Users\\Fabrizio Ortega\\git\\MarketSystem\\MarketSystem\\resources\\data\\EURUSD-PERIOD_M15_m.csv");
		List<MarketData> ds = m.split(4);
		int a=0;
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
