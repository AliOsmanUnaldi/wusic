package com.estu.wusic.api.controllers;

import com.estu.wusic.business.abstracts.RoomService;
import com.estu.wusic.business.dtos.roomDtos.RoomByIdDto;
import com.estu.wusic.business.dtos.roomDtos.RoomListDto;
import com.estu.wusic.business.requests.roomRequests.CreateRoomRequest;
import com.estu.wusic.business.requests.roomRequests.UpdateRoomRequest;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

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
    public Result add(@RequestBody CreateRoomRequest createRoomRequest){

        return this.roomService.add(createRoomRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateRoomRequest updateRoomRequest){

        return this.roomService.update(updateRoomRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam int id){

        return this.roomService.delete(id);
    }

    @GetMapping("/getRoomByRoomId")
    public DataResult<RoomByIdDto> getRoomByRoomId(@RequestParam int id){

        return this.roomService.getRoomByRoomId(id);
    }
}
