package com.estu.wusic.dataAccess.abstracts;

import com.estu.wusic.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDao extends JpaRepository<Room,Integer> {
}
