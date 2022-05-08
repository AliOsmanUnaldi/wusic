package com.estu.wusic.api.controllers;

import com.estu.wusic.business.abstracts.CityService;
import com.estu.wusic.business.requests.cityRequests.CreateCityRequest;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.City;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {
    private CityService cityService;

    public CitiesController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/getAll")
    public DataResult<List<City>> getAll(){

       return this.cityService.getAll();
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateCityRequest createCityRequest){

        return this.cityService.add(createCityRequest);
    }

    @GetMapping("/getCityById")
    public DataResult<City> getCityById(@RequestParam int id){

        return this.cityService.getCityById(id);
    }

    @GetMapping("/getCityByCityName")
    public DataResult<City> getCityByCityName(@RequestParam String cityName){

        return this.cityService.getCityByCityName(cityName);
    }
}
