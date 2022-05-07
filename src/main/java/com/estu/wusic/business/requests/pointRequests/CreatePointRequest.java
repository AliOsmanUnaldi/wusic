package com.estu.wusic.business.requests.pointRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePointRequest {

    @NotNull
    private int pointsOwnerId;

    @NotNull
    private int pointsRecieverId;

    @NotNull
    @Min(0)
    @Max(100)
    private double givenPoint;
}
