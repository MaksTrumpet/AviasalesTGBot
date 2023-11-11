package MTCompany.utils;

import MTCompany.DispatcherApplication;
import MTCompany.model.UserRequestModel;
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
    private static Map<String, String> cityToIATA;

    @PostConstruct
    private void loadCityData() {
        cityToIATA = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(DispatcherApplication
                .class.getResourceAsStream("/cities.csv"))))) {
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

    public boolean convertCityToIATA(UserRequestModel userRequestModel, String queryField) {

        switch (queryField) {
            case "departureCity":
                return convertDepartureCity(userRequestModel);
            case "arrivalCity":
                return convertArrivalCity(userRequestModel);
            default:
                log.error("Unexpected value: " + queryField);
                throw new IllegalStateException("Unexpected value: " + queryField);
        }
    }

    private boolean convertDepartureCity(UserRequestModel userRequestModel) {
        String key = userRequestModel.getDepartureCity().toLowerCase();
        if (cityToIATA.containsKey(key)) {
            userRequestModel.setDepartureCity(cityToIATA.get(key));
            return true;
        }
        return false;
    }

    private boolean convertArrivalCity(UserRequestModel userRequestModel) {
        String key = userRequestModel.getArrivalCity().toLowerCase();
        if (cityToIATA.containsKey(key)) {
            userRequestModel.setArrivalCity(cityToIATA.get(key));
            return true;
        }
        return false;
    }
}
