package com.estu.wusic.dataAccess.abstracts;

import com.estu.wusic.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<City,Integer> {

    City getCityByCity(String city);
}
