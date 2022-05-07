package com.estu.wusic.business.abstracts;

import com.estu.wusic.business.dtos.roomDtos.RoomByIdDto;
import com.estu.wusic.business.dtos.roomDtos.RoomListDto;
import com.estu.wusic.business.requests.roomRequests.CreateRoomRequest;
import com.estu.wusic.business.requests.roomRequests.UpdateRoomRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.Room;

import java.util.List;

public interface RoomService {

    DataResult<List<RoomListDto>> getAll();

    DataResult<List<RoomListDto>> getAllRoomsByCityName(String city);

    Result add(CreateRoomRequest createRoomRequest);

    Result update(UpdateRoomRequest updateRoomRequest);

    Result delete(int id);

    DataResult<RoomByIdDto> getRoomDtoByRoomId(int id);

    Room getRoomByRoomId(int roomId) throws BusinessException;

    void save (Room room);

    boolean checkIfRoomExists(int roomId);

}
