package com.estu.wusic.business.requests.roomRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoomRequest {

    private int ownerId;

    private String roomName;

    private String genre;

    private int city;

    private String password;

    private String description;
}
