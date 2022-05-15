package com.estu.wusic.business.abstracts;

import com.estu.wusic.business.dtos.pointDtos.PointByIdDto;
import com.estu.wusic.business.dtos.pointDtos.PointListDto;
import com.estu.wusic.business.requests.pointRequests.CreatePointRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.Point;

import java.util.List;


public interface PointService {
    Result add(CreatePointRequest createPointRequest) throws BusinessException;
    DataResult<PointByIdDto> getPointByPointId(int id);
    DataResult<List<PointListDto>> getAllByPointsRecieverId(int id);
    DataResult<Double> getAvaragePointOfHost(int userId);
    void save (Point point);
}
