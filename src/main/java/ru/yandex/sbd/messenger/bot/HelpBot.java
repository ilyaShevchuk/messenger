package ru.yandex.sbd.messenger.bot;

import ru.yandex.sbd.messenger.config.csvConfig.UserInConfig;
import ru.yandex.sbd.messenger.exceptions.BadParamsException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class HelpBot {
    public String givePersonalizeGreeting(UserInConfig user) {
        //todo : изменить этот класс, меньше текса , считывания из файла с конфигом
        // fix encoding problems
        ZoneId userZoneId = ZoneId.of("UTC" + user.utc);
        ZonedDateTime now = ZonedDateTime.now(userZoneId);
        int hour = now.getHour();
        String timeOfDay = "";
        if (hour >= 0 && hour < 12) {
            timeOfDay = user.local.equals("En") ? "morning" : "утром";
        } else if (hour >= 12 && hour <= 16) {
            timeOfDay = user.local.equals("En") ? "afternoon" : "днём";
        } else if (hour > 16 && hour < 21) {
            timeOfDay = user.local.equals("En") ? "evening" : "вечером";
        } else if (hour >= 21 && hour < 24) {
            timeOfDay = user.local.equals("En") ? "night" : "ночью";
        }
        if (user.local.equals("En")) {
            return String.format("Hello %s,Today's %s database doesnt exist . We will fix it soon",
                    user.username, timeOfDay);
        }
        if (user.local.equals("Ru")) {
            return String.format("Привет %s,Сегодняшним %s база данных недоступна. Мы скоро починим это)",
                    user.username, timeOfDay);
        }
        throw new BadParamsException("Users csv is wrong " + user);
    }

    public String giveDefaultGreeting() {
        return "Database doesnt exist . We will fix it soon";
    }
}