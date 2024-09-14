package com.interview.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDto {
    private Long id;
    private String screenName;
    private String gender;
    private Integer age;
    private String login;
    private String password;
    private String role;
}
