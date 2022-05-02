package com.estu.wusic.dataAccess.abstracts;


import com.estu.wusic.entities.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointDao extends JpaRepository<Point,Integer> {

    public List<Point> getAllByPointsRecieverId(int id);
}
