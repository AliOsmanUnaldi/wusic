package com.estu.wusic.business.dtos.pointDtos;

import com.estu.wusic.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointListDto {

    private int id;
    private int pointsOwnerId;
    private int pointsRecieverId;
    private double givenPoint;
}
