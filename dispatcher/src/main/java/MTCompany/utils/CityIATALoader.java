package MTCompany.utils;

import MTCompany.DispatcherApplication;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Log4j
@Getter
@Component
public class CityIATALoader {
    private Map<String, String> cityToIATA;

    @PostConstruct
    public void loadCityData() {
        cityToIATA = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(DispatcherApplication
                .class.getResourceAsStream("/cities(1).csv"))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String city = parts[0];
                    String iata = parts[1];
                    cityToIATA.put(city, iata);
                } else {
                    System.out.println("ignoring line: " + line);
                }
            }
        } catch (Exception e) {
            log.error(String.format(
                    "Exception occurred trying to read '%s'.", "cities(1).csv") + e);
        }

    }

    public static String ConvertCityToIATA() {
        String output;

    }
}
