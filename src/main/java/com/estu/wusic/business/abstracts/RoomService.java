package com.estu.wusic.business.abstracts;

import com.estu.wusic.business.dtos.roomDtos.RoomByIdDto;
import com.estu.wusic.business.dtos.roomDtos.RoomListDto;
import com.estu.wusic.business.requests.roomRequests.CreateRoomRequest;
import com.estu.wusic.business.requests.roomRequests.UpdateRoomRequest;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.Room;

import java.util.List;

public interface RoomService {

    DataResult<List<RoomListDto>> getAll();

    Result add(CreateRoomRequest createRoomRequest);

    Result update(UpdateRoomRequest updateRoomRequest);

    Result delete(int id);

    DataResult<RoomByIdDto> getRoomDtoByRoomId(int id);

    Room getRoomByRoomId(int roomId);

    void save (Room room);

}
