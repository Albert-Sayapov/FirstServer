import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    public static void main(String[] args) {
        List<String> cityList = new ArrayList<>();
        String city = null;
        char letter = ' ';
        try (ServerSocket server = new ServerSocket(ServerConfig.PORT)) {
            System.out.println("Сервер запущен!");
            while (true) {

                try (Socket client = server.accept();
                     PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                    System.out.println("Новое подключение, порт: " + client.getPort());

                    if (city == null) {
                        writer.println("Вы первый! Введите любой город");
                        city = reader.readLine();
                        cityList.add(city.toLowerCase());
                        letter = city
                                .toLowerCase()
                                .charAt(city.length() - 1);
                    } else {
                        writer.println("Был введен город " + city);
                        String newCity = reader.readLine();
                        char newCityLetter = newCity
                                .toLowerCase()
                                .charAt(0);
                        if (newCityLetter == letter) {
                            if (!cityList.contains(newCity.toLowerCase())) {
                                writer.println("Верно!");
                                city = newCity;
                                cityList.add(city.toLowerCase());
                                letter = city
                                        .toLowerCase()
                                        .charAt(city.length() - 1);
                            } else {
                                writer.println("Такой город уже был введен");
                            }
                        } else {
                            writer.println("Не верно!");
                        }
                    }
                }
                System.out.println(cityList);
            }
        } catch (IOException e) {
            System.out.println("Не могу запустить срвер");
            e.printStackTrace();
        }
    }
}
