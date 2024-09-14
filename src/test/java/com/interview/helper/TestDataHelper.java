package com.interview.helper;

import com.interview.config.ConfigurationCollector;
import com.interview.dto.PlayerDto;
import com.interview.enums.UserType;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

import static com.interview.constants.StringConstants.AGE;
import static com.interview.constants.StringConstants.DOT;
import static com.interview.constants.StringConstants.GENDER;
import static com.interview.constants.StringConstants.ID;
import static com.interview.constants.StringConstants.LOGIN;
import static com.interview.constants.StringConstants.PASSWORD;
import static com.interview.constants.StringConstants.ROLE;
import static com.interview.constants.StringConstants.SCREEN_NAME;

@UtilityClass
public class TestDataHelper {

    private static final String[] GENDERS = {"male", "female"};
    private static final String[] ROLES = {"admin", "user"};
    public static final String USERS_KEY = "users";

    public static PlayerDto createRandomPlayerDto() {
        return PlayerDto.builder()
                .screenName(generateRandomString(10))
                .age(generateRandomIntForRange(18, 99))
                .gender(getRandomValueOfArray(GENDERS))
                .login(generateRandomString(8))
                .password(generateRandomString(12))
                .role(getRandomValueOfArray(ROLES))
                .build();
    }

    private static String generateRandomString(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    private static Integer generateRandomIntForRange(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    private static String getRandomValueOfArray(String[] arr) {
        return arr[new Random().nextInt(arr.length)];
    }

    public static PlayerDto createPlayerDtoByUserType(UserType userType) {
        String userConfigKey = USERS_KEY + DOT + userType.getValue() + DOT;
        return PlayerDto.builder()
                .id(Long.parseLong(ConfigurationCollector.getProperty(userConfigKey + ID)))
                .login(ConfigurationCollector.getProperty(userConfigKey + LOGIN))
                .password(ConfigurationCollector.getProperty(userConfigKey + PASSWORD))
                .screenName(ConfigurationCollector.getProperty(userConfigKey + SCREEN_NAME))
                .gender(ConfigurationCollector.getProperty(userConfigKey + GENDER))
                .age(Integer.parseInt(ConfigurationCollector.getProperty(userConfigKey + AGE)))
                .role(ConfigurationCollector.getProperty(userConfigKey + ROLE))
                .build();
    }
}
