package com.estu.wusic.business.requests.userRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank
    @NotNull
    private String userName;

    @Min(5)
    @NotNull
    private String password;

    @Email
    @NotNull
    private String email;
}
