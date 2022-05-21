package com.estu.wusic.dataAccess.abstracts;

import com.estu.wusic.business.dtos.userDtos.UserListDto;
import com.estu.wusic.entities.Room;
import com.estu.wusic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface RoomDao extends JpaRepository<Room,Integer> {
    public List<Room> getAllByCity_City(String city);
    Room getRoomByOwner_Id(int ownerId);
    List<Room> getRoomByOwner_IdAndCreationDate(int ownerId, LocalDate date);
}
