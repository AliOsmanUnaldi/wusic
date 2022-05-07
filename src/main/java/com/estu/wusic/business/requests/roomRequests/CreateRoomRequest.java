package com.estu.wusic.business.requests.roomRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {

    @NotNull
    private int ownerId;

    @NotNull
    @NotBlank
    private String roomName;

    @NotNull
    @NotBlank
    private String genre;

    @NotNull
    @Positive
    private int city;

    private String password;

    private String description;
}
