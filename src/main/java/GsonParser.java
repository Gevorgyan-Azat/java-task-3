import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class GsonParser {

    public Companies parse() {

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\Shares.json"))) {

            String json = "";
            String jsonFile = reader.readLine();
            while (jsonFile != null) {
                json += jsonFile;
                jsonFile = reader.readLine();
            }
            Json companies = gson.fromJson(json, Json.class);
            LocalDate date = LocalDate.now();
            AtomicInteger totalSecurities = new AtomicInteger();
            for (Companies s: companies.getCompanies()) {
                System.out.println(s.getName() + " - " + s.getFounded().format(DateTimeFormatter.ofPattern("dd/MM/yy")));
            }
            System.out.println("");
            for (Companies f: companies.getCompanies()){
                System.out.println(f.getName() + ":");
                f.getSecurities().stream().filter((s)-> s.getDate().isBefore(date)).forEach((s)->{System.out.println("Код: " + s.getCode() + ", дата истечения: " + s.getDate());
                totalSecurities.getAndIncrement();});
                System.out.println(" ");
            }
            System.out.println("Просрочено: " + totalSecurities);

            Scanner scanner = new Scanner(System.in);
            System.out.println("");
            System.out.println("Введите дату:");
            String stringDate = scanner.next();
            LocalDate userDate = null;
            try {
                userDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (Exception ignored) {}
            try {
                userDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd.MM.yy"));
                if (userDate.getYear() - LocalDate.now().getYear() >1) {
                    userDate = userDate.minusYears(100);
                }
            } catch (Exception ignored) {}
            try {
                userDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (Exception ignored) {}
            try {
                userDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd/MM/yy"));
                if (userDate.getYear() - LocalDate.now().getYear() >1) {
                    userDate = userDate.minusYears(100);
                }
            } catch (Exception ignored) {}
            LocalDate finalUserDate = userDate;
            companies.getCompanies().stream().filter((s)-> s.getFounded().isAfter(finalUserDate)).forEach((s)->System.out.println(s.getName() +" "+ s.getFounded()));

            System.out.println("");
            System.out.println("Введите валюту: \"RUB\", \"USD\" или \"EU\"");
            String stringCurrency = scanner.next();
            for (Companies f: companies.getCompanies()){
                System.out.println(f.getName() + ":");
                f.getSecurities().stream().filter((s)-> s.getCurrency().stream().anyMatch((e) ->
                        e.equalsIgnoreCase(stringCurrency))).forEach((s)->System.out.println("Код: " + s.getCode()));
                System.out.println(" ");
            }



        } catch (Exception ioException) {
            ioException.printStackTrace();
        }
        return null;



    }
}
