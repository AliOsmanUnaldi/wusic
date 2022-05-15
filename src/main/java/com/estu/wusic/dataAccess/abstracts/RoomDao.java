package com.estu.wusic.dataAccess.abstracts;

import com.estu.wusic.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomDao extends JpaRepository<Room,Integer> {
    public List<Room> getAllByCity_City(String city);
    Room getRoomByOwner_Id(int ownerId);
}
