package com.estu.wusic.business.concretes;

import com.estu.wusic.business.abstracts.RoomService;
import com.estu.wusic.business.abstracts.UserService;
import com.estu.wusic.business.dtos.roomDtos.RoomByIdDto;
import com.estu.wusic.business.dtos.roomDtos.RoomListDto;
import com.estu.wusic.business.requests.roomRequests.CreateRoomRequest;
import com.estu.wusic.business.requests.roomRequests.UpdateRoomRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.mapping.ModelMapperService;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.core.utilities.results.SuccessDataResult;
import com.estu.wusic.core.utilities.results.SuccessResult;
import com.estu.wusic.dataAccess.abstracts.CityDao;
import com.estu.wusic.dataAccess.abstracts.RoomDao;
import com.estu.wusic.entities.Room;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomManager implements RoomService {

    private RoomDao roomDao;
    private ModelMapperService modelMapperService;
    private UserService userService;
    private CityDao cityDao;

    public RoomManager(RoomDao roomDao, ModelMapperService modelMapperService, UserService userService,CityDao cityDao) {

        this.roomDao = roomDao;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
        this.cityDao = cityDao;
    }

    @Override
    public DataResult<List<RoomListDto>> getAll() {

        List<Room> result = this.roomDao.findAll();
        List<RoomListDto> response = result.stream()
                .map(room -> this.modelMapperService.forDto().map(room,RoomListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<RoomListDto>>(response,"Tüm odalar listelendi.");
    }

    @Override
    public DataResult<List<RoomListDto>> getAllRoomsByCityName(String city) {
        List<Room> result = this.roomDao.getAllByCity_City(city);
        List<RoomListDto> response = result.stream()
                .map(room -> this.modelMapperService.forDto().map(room,RoomListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<RoomListDto>>(response,city+" şehrindeki tüm odalar listelendi.");
    }


    @Override
    public Result add(CreateRoomRequest createRoomRequest) {

        System.out.println(this.cityDao.findAll());

        Room room = this.modelMapperService.forRequest().map(createRoomRequest,Room.class);
        room.setCity(this.cityDao.getById(createRoomRequest.getCity()));

        this.roomDao.save(room);

        return new SuccessResult("Oda başarılı bir şekilde oluşturuldu.");
    }

    @Override
    public Result update(UpdateRoomRequest updateRoomRequest) {

        Room room = this.modelMapperService.forRequest().map(updateRoomRequest,Room.class);

        this.roomDao.save(room);

        return new SuccessResult("Oda başarıyla güncellendi.");
    }

    @Override
    public Result delete(int id) {

        this.roomDao.deleteById(id);

        return new SuccessResult("Oda başarıyla silindi");
    }

    @Override
    public DataResult<RoomByIdDto> getRoomDtoByRoomId(int id) {

        Room room = this.roomDao.getById(id);
        RoomByIdDto response = this.modelMapperService.forDto().map(room,RoomByIdDto.class);

        return new SuccessDataResult<RoomByIdDto>(response,"Oda id kullanarak bulundu.");
    }

    @Override
    public Room getRoomByRoomId(int roomId) throws BusinessException {

        if (!checkIfRoomExists(roomId)){
            throw new BusinessException("Oda bulunamadı.");
        }

        return this.roomDao.getById(roomId);
    }

    @Override
    public void save(Room room) {

        this.roomDao.save(room);
    }

    @Override
    public boolean checkIfRoomExists(int roomId){

        return this.roomDao.existsById(roomId);
    }

}
