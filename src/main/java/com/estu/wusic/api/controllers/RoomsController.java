package com.estu.wusic.api.controllers;

import com.estu.wusic.business.abstracts.RoomService;
import com.estu.wusic.business.dtos.roomDtos.RoomByIdDto;
import com.estu.wusic.business.dtos.roomDtos.RoomListDto;
import com.estu.wusic.business.requests.roomRequests.CreateRoomRequest;
import com.estu.wusic.business.requests.roomRequests.UpdateRoomRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomsController {

    private RoomService roomService;

    public RoomsController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/getAll")
    public DataResult<List<RoomListDto>> getAll(){

        return this.roomService.getAll();
    }

    @PostMapping("/add")
    public DataResult<Integer> add(@RequestBody @Valid CreateRoomRequest createRoomRequest) throws BusinessException {

        return this.roomService.add(createRoomRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateRoomRequest updateRoomRequest) throws BusinessException {

        return this.roomService.update(updateRoomRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int id){

        return this.roomService.delete(id);
    }

    @GetMapping("/getRoomByRoomId")
    public DataResult<RoomByIdDto> getRoomByRoomId(@RequestParam int id){

        return this.roomService.getRoomDtoByRoomId(id);
    }

    @GetMapping("/getAllByCityName")
    public DataResult<List<RoomListDto>> getAllRoomsByCity(@RequestParam String city, @RequestParam int userId) throws BusinessException {

        return this.roomService.getAllRoomsByCityName(city,userId);
    }

    @GetMapping("/getAllParticipants")
    public List<String> getAllParticipants(@RequestParam int roomId){

        return this.roomService.getAllParticipantsByRoomId(roomId);
    }

    @GetMapping("/getRoomsByOwner_Id")
    public DataResult<Integer> getRoomsByOwner_Id(@RequestParam int ownerId) throws BusinessException{

        return this.roomService.getRoomsByOwner_Id(ownerId);
    }
}
