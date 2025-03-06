import com.maxmind.geoip2.exception.GeoIp2Exception;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;
import java.net.InetAddress;

public class WeatherAppGUI extends JFrame {

    private JPanel inputPanel;
    private JPanel buttonPanel;
    private JPanel displayPanelWeather;
    private JPanel displayPanelUsersWeather;


    private String city;


    private JTextArea usersWeatherDataTextArea;


    private InetAddress usersIP;

    private JButton requestWeatherButton;

    private JTextField requestWeatherCityTextField;

    private JTextArea displayTextArea;


    public WeatherAppGUI() {
        setTitle("Weather App");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        createInputPanel();
        mainPanel.add(inputPanel, BorderLayout.NORTH);

        createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        createDisplayPanelWeather();
        mainPanel.add(displayPanelWeather, BorderLayout.CENTER);

        createDisplayPanelUsersWeather();
        mainPanel.add(displayPanelUsersWeather, BorderLayout.WEST);

        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void createInputPanel() {

        inputPanel = new JPanel();

        requestWeatherCityTextField = new JTextField();
        requestWeatherCityTextField.setBorder(BorderFactory.createLineBorder(Color.black));
        requestWeatherCityTextField.setPreferredSize(new Dimension(250, 30));

        inputPanel.add(requestWeatherCityTextField, BorderLayout.CENTER);


    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());


        requestWeatherButton = new JButton("Request");
        requestWeatherButton.addActionListener(this::callWeatherAPI);
        buttonPanel.add(requestWeatherButton);
    }

    private void callWeatherAPI(ActionEvent actionEvent) {
        WeatherAPI weatherAPI = new WeatherAPI();
        city = requestWeatherCityTextField.getText();
        String jsonResponse = weatherAPI.requestWeather(city);

        String weatherData = WeatherParser.getWeatherJSON(jsonResponse);
        displayTextArea.setText("");
        displayTextArea.append(weatherData);
    }

    private void createDisplayPanelWeather() {
        displayPanelWeather = new JPanel();

        displayTextArea = new JTextArea();
        displayTextArea.setLayout(new BorderLayout());
        displayTextArea.setPreferredSize(new Dimension(250, 300));
        displayTextArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Weather  " + city,
                TitledBorder.CENTER,
                TitledBorder.TOP
        ));
        displayTextArea.setEditable(false);
        displayTextArea.setLineWrap(true);
        displayTextArea.setWrapStyleWord(true);
        displayPanelWeather.add(displayTextArea);

    }

    private void createDisplayPanelUsersWeather() {
        displayPanelUsersWeather = new JPanel();

        usersWeatherDataTextArea = new JTextArea();
        usersWeatherDataTextArea.setLayout(new BorderLayout());
        usersWeatherDataTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
        usersWeatherDataTextArea.setPreferredSize(new Dimension(250, 250));
        usersWeatherDataTextArea.setEditable(false);
        usersWeatherDataTextArea.setLineWrap(true);
        usersWeatherDataTextArea.setWrapStyleWord(true);

        usersWeatherDataTextArea.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Weather for " + getUsersCity(),
                TitledBorder.CENTER,
                TitledBorder.TOP
        ));
        try {
            String usersWeatherData = getUsersWeather();
            usersWeatherDataTextArea.setText(usersWeatherData);

            displayPanelUsersWeather.add(usersWeatherDataTextArea);

        } catch (IOException | GeoIp2Exception e) {
            throw new RuntimeException(e);
        }
    }




    private String getUsersWeather() throws IOException, GeoIp2Exception {
        WeatherAPI weatherAPI = new WeatherAPI();
        String jsonResponse = weatherAPI.getUsersWeather();

        return WeatherParser.getWeatherJSON(jsonResponse);
    }

    private void displayCity() {
        displayTextArea.setText(Objects.requireNonNullElse(city, ""));
    }


    private String getUsersCity() {
        WeatherAPI weatherAPI = new WeatherAPI();
        try {
            return weatherAPI.data();

        } catch (IOException | GeoIp2Exception e) {
            throw new RuntimeException(e);
        }
    }

}