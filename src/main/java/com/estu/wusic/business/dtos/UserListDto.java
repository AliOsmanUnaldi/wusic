package com.estu.wusic.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {

    private int id;
    private String userName;
    private String password;
    private String email;
    private boolean isJoined;
}
