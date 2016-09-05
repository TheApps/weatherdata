import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class WeatherData {

	static int MAX_SIZE = 10;
	final static String beginDateTime = "2016-01-01 00:00:00";
	final static String endDateTime = "2016-01-01 23:59:59";

	final static int CONDITION_SIZE = 3;
	final static String[] CONDITIONS= {"Rain", "Snow", "Sunny"};

	final static float RAIN_MIN_TEMP = 0.0f;
	final static float RAIN_MAX_TEMP = 30.0f;
	final static float RAIN_MIN_PRES = 1000.0f;
	final static float RAIN_MAX_PRES = 1100.0f;
	final static int RAIN_MIN_HUMI = 75;
	final static int RAIN_MAX_HUMI = 100;

	final static float SNOW_MIN_TEMP = -50.0f;
	final static float SNOW_MAX_TEMP = 0.0f;
	final static float SNOW_MIN_PRES = 900.0f;
	final static float SNOW_MAX_PRES = 1000.0f;
	final static int SNOW_MIN_HUMI = 45;
	final static int SNOW_MAX_HUMI = 75;

	final static float SUNNY_MIN_TEMP = 30.0f;
	final static float SUNNY_MAX_TEMP = 50.0f;
	final static float SUNNY_MIN_PRES = 1100.0f;
	final static float SUNNY_MAX_PRES = 1200.0f;
	final static int SUNNY_MIN_HUMI = 0;
	final static int SUNNY_MAX_HUMI = 45;

	final static int MIN_LAT = -90;
	final static int MIN_LONG = -180;
	final static int MIN_ELEV = 1;

	final static int MAX_LAT = 90;
	final static int MAX_LONG = 180;
	final static int MAX_ELEV = 100;

	public static void main(String args[]) throws IOException 
	{

		float[] latitude = new float[MAX_SIZE];
		float[] longitude = new float[MAX_SIZE];
		float[] elevation = new float[MAX_SIZE];
		String[] location = new String[MAX_SIZE];
		String[][] tempPresHumi = new String[MAX_SIZE][3+1];
		Timestamp[] localTime = new Timestamp[MAX_SIZE];

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please Enter the target file path:\n");
		String path = br.readLine();

		generatelatitude(latitude,MIN_LAT,MAX_LAT);
		generatelongitude(longitude,MIN_LONG,MAX_LONG);
		generateelevation(elevation,MIN_ELEV,MAX_ELEV);
		generatelocation(location);
		generateTempPresHumi(tempPresHumi);
		generateDate(localTime);


		File file = new File(path);
		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

		for (int i = 0; i < MAX_SIZE; i++) 
		{
			bw.write(location[i] +"|"+ latitude[i]+","+ longitude[i] +","+ elevation[i] +"|"+convertTimestampToISO(localTime[i]) +"|"+ tempPresHumi[i][3] +"|"+ tempPresHumi[i][0] +"|"+ tempPresHumi[i][1] +"|"+ tempPresHumi[i][2]);
			bw.newLine();
		}
		bw.close();

		System.out.println("Weather Data Generated");
	}

	private static String convertTimestampToISO(Timestamp ts) {
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); 
		df.setTimeZone(tz);
		String nowAsISO = df.format(ts);
		return nowAsISO;

	}

	private static void generateDate(Timestamp[] localTime) 
	{
		long rangebegin = Timestamp.valueOf(beginDateTime).getTime();
		long rangeend = Timestamp.valueOf(endDateTime).getTime();
		long diff = rangeend - rangebegin + 1;
		for (int i = 0; i < localTime.length; i++)
		{
			localTime[i] = new Timestamp(rangebegin + (long)(Math.random() * diff));
		}
	}


	private static void generateTempPresHumi(String[][] tempPresHumi) {

		for (int i = 0; i < tempPresHumi.length; i++)
		{
			int condition = (int) randomRange(0, CONDITION_SIZE);
			if(condition==0)
			{
				//0 means rain
				tempPresHumi[i][0] = String.valueOf(randomRange(RAIN_MIN_TEMP, RAIN_MAX_TEMP));
				tempPresHumi[i][1] = String.valueOf(randomRange(RAIN_MIN_PRES, RAIN_MAX_PRES));
				tempPresHumi[i][2] = String.valueOf(randomRange(RAIN_MIN_HUMI, RAIN_MAX_HUMI));
				tempPresHumi[i][3] = "RAIN";
			}
			else if(condition==1)
			{
				//1 means snow
				tempPresHumi[i][0] = String.valueOf(randomRange(SNOW_MIN_TEMP, SNOW_MAX_TEMP));
				tempPresHumi[i][1] = String.valueOf(randomRange(SNOW_MIN_PRES, SNOW_MAX_PRES));
				tempPresHumi[i][2] = String.valueOf(randomRange(SNOW_MIN_HUMI, SNOW_MAX_HUMI));
				tempPresHumi[i][3] = "SNOW";
			}
			else
			{
				//2 means sunny
				tempPresHumi[i][0] = String.valueOf(randomRange(SUNNY_MIN_TEMP, SUNNY_MAX_TEMP));
				tempPresHumi[i][1] = String.valueOf(randomRange(SUNNY_MIN_PRES, SUNNY_MAX_PRES));
				tempPresHumi[i][2] = String.valueOf(randomRange(SUNNY_MIN_HUMI, SUNNY_MAX_HUMI));
				tempPresHumi[i][3] = "SUNNY";
			}
		}

	}

	private static void generatelocation(String[] location) {

		for (int i = 0; i < location.length; i++)
		{
			location[i] = "Location"+i;
		}		
	}

	private static void generateelevation(float[] elevation, float min, float max) 
	{
		for (int i = 0; i < elevation.length; i++)
		{
			elevation[i] = randomRange(min,max);
		}
	}

	private static void generatelongitude(float[] longitude, float min, float max)
	{
		for (int i = 0; i < longitude.length; i++)
		{
			longitude[i] = randomRange(min,max);
		}
	}

	private static void generatelatitude(float[] latitude, float min, float max)
	{
		for (int i = 0; i < latitude.length; i++)
		{
			latitude[i] = randomRange(min,max);
		}
	}

	public static float randomRange(float min, float max) 
	{
		return (float) Math.round((((Math.random() * (max - min)) + min))*100)/100.00f;
	}
}
