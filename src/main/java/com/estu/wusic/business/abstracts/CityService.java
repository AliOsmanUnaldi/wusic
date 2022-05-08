package com.estu.wusic.business.abstracts;

import com.estu.wusic.business.requests.cityRequests.CreateCityRequest;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.City;

import java.util.List;

public interface CityService {

    Result add(CreateCityRequest createCityRequest);
    DataResult<List<City>> getAll();
    DataResult<City> getCityById(int id);
    DataResult<City> getCityByCityName(String cityName);
}
