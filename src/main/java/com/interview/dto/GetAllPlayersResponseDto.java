package com.interview.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetAllPlayersResponseDto {
    private List<PlayerDto> players;
}
