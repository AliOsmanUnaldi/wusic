package com.estu.wusic.api.controllers;

import com.estu.wusic.business.abstracts.PointService;
import com.estu.wusic.business.dtos.pointDtos.PointByIdDto;
import com.estu.wusic.business.dtos.pointDtos.PointListDto;
import com.estu.wusic.business.requests.pointRequests.CreatePointRequest;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ponints")
public class PointsController {

    private PointService pointService;

    public PointsController(PointService pointService) {
        this.pointService = pointService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreatePointRequest createPointRequest){
        return this.pointService.add(createPointRequest);
    }

    @GetMapping("/getById")
    public DataResult<PointByIdDto> getById(@RequestParam int id){
        return this.pointService.getPointByPointId(id);
    }

    @GetMapping("/getAllByRecieversId")
    public DataResult<List<PointListDto>> getAllByRecieversId(@RequestParam int id){
        return this.pointService.getAllByPointsRecieverId(id);
    }
}
