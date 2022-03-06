package com.estu.wusic.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private int id;
    private String userName;
    private String password;
    private String email;
    private boolean isJoined;
}
