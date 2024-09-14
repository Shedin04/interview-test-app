package com.interview.helper;

import com.interview.dto.PlayerDto;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

@UtilityClass
public class TestDataHelper {

    private static final String[] GENDERS = {"male", "female"};
    private static final String[] ROLES = {"admin", "user"};

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
}
