package com.estu.wusic.business.concretes;

import com.estu.wusic.business.abstracts.CityService;
import com.estu.wusic.business.requests.cityRequests.CreateCityRequest;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.core.utilities.results.SuccessDataResult;
import com.estu.wusic.core.utilities.results.SuccessResult;
import com.estu.wusic.dataAccess.abstracts.CityDao;
import com.estu.wusic.entities.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityManager implements CityService {

    private CityDao cityDao;

    public CityManager(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public Result add(CreateCityRequest createCityRequest) {

        City city = new City();
        city.setCity(createCityRequest.getCity().toUpperCase());
        this.cityDao.save(city);
        return new SuccessResult("Şehir başarıyla eklendi");
    }

    @Override
    public DataResult<List<City>> getAll() {

        return new SuccessDataResult<List<City>>(this.cityDao.findAll(),"Tüm şehirler başarıyla liistelendi");
    }

    @Override
    public DataResult<City> getCityById(int id) {

        return new SuccessDataResult<City>(this.cityDao.getById(id),"Şehir id kullanarak bulundu.");
    }

    @Override
    public DataResult<City> getCityByCityName(String cityName) {

        City city = this.cityDao.getCityByCity(cityName);

        return new SuccessDataResult<City>(city,"Şehir başarıyla getirildi.");
    }
}
