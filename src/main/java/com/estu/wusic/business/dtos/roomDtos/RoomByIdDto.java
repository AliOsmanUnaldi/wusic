package com.estu.wusic.business.dtos.roomDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomByIdDto {

    private int ownerId;

    private String roomName;

    private String genre;

    private String location;

    private String description;
}
