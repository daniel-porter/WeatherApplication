import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;


public class WeatherAPI {

    private static final String Base_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "0b829abb018afe46bef7d39215e83704";
    public String geoipCity;
    private String usersPublicIP;


    public String requestWeather(String city) {
        try {
            String weatherURL = Base_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric";

            URL url = new URL(weatherURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
return response.toString();
        } catch (Exception e) {
            return null;
        }

        }

        public String getUsersWeather() {

        try {
            String usersCity = data();

            if (usersCity == null) {
                System.out.println("could not get users city");
                return null;
            }

            return requestWeather(usersCity);

            } catch (Exception e) {

                System.out.println("getUsersWeather Exception");
            return null;
            }
        }

    public String data() throws IOException, GeoIp2Exception {


        getIPaddress();

        String dbLocation = "src/main/java/Database/GeoLite2-City.mmdb";

        File database = new File(dbLocation);
        DatabaseReader dbr = new DatabaseReader.Builder(database).build();

        InetAddress ipA = InetAddress.getByName(usersPublicIP);
        CityResponse response = dbr.city(ipA);


        geoipCity = response.getCity().getName();
        return geoipCity;

    }

    private String getIPaddress() {

        try {
            URL url = new URL("https://checkip.amazonaws.com/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            usersPublicIP = reader.readLine().trim();
            reader.close();


            return usersPublicIP;

        }catch (Exception e) {
            return null;
        }

    }
    }


