package ru.yandex.sbd.messenger.config.csvConfig;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigReader {

    private final Path filePath;

    private final Map<String, UserInConfig> users = new HashMap<>();

    public ConfigReader() {
        this.filePath = Path.of("users.csv");
    }

    public Map<String, UserInConfig> getUsersInConfig() {
        return users;
    }

    public void readConfig() {
        CSVReader reader;
        try {
            reader = new CSVReader(new FileReader(String.valueOf(filePath)));
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                UserInConfig user = new UserInConfig(nextLine);
                this.users.put(user.userId, user);
            }
        } catch (IOException e) {
            //logger
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
