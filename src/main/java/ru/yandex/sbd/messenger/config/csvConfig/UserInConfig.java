package ru.yandex.sbd.messenger.config.csvConfig;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UserInConfig {
    @Getter
    public String userId;
    @Getter
    public String username;
    @Getter
    public String utc;
    @Getter
    public String sex;
    @Getter
    public String local;

    public UserInConfig(String[] nextLine) {
        this.userId = nextLine[0];
        this.username = nextLine[1];
        this.utc = nextLine[2];
        this.sex = nextLine[3];
        this.local = nextLine[4];
    }
}
