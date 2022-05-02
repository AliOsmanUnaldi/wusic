package com.estu.wusic.business.requests.pointRequests;

import com.estu.wusic.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePointRequest {

    private int pointsOwnerId;
    private int pointsRecieverId;
    private double givenPoint;
}
