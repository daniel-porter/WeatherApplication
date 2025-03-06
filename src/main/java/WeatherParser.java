import netscape.javascript.JSException;
import netscape.javascript.JSObject;
import org.json.JSONObject;


public class WeatherParser {



    public static String getWeatherJSON(String jsonResponse) {
        JSONObject jsonObject = new JSONObject(jsonResponse);

String city = jsonObject.getString("name");
String country = jsonObject.getJSONObject("sys").getString("country");
double temperature = jsonObject.getJSONObject("main").getDouble("temp");
double humidity = jsonObject.getJSONObject("main").getDouble("humidity");
System.out.println(jsonResponse);



return "City: " + city + "\nCountry: " + country + "\nTemperature: " + temperature + "\nHumidity: " + humidity;

    }





    }
