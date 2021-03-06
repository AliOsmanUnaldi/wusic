package com.estu.wusic.business.concretes;

import com.estu.wusic.business.abstracts.PointService;
import com.estu.wusic.business.abstracts.RoomService;
import com.estu.wusic.business.abstracts.UserService;
import com.estu.wusic.business.dtos.roomDtos.RoomByIdDto;
import com.estu.wusic.business.dtos.roomDtos.RoomListDto;
import com.estu.wusic.business.dtos.userDtos.UserListDto;
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
import com.estu.wusic.entities.City;
import com.estu.wusic.entities.Room;
import com.estu.wusic.entities.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomManager implements RoomService {

    private RoomDao roomDao;
    private ModelMapperService modelMapperService;
    private UserService userService;
    private CityDao cityDao;

    private PointService pointService;

    public RoomManager(RoomDao roomDao, ModelMapperService modelMapperService,@Lazy UserService userService, CityDao cityDao,
                       @Lazy PointService pointService) {

        this.roomDao = roomDao;
        this.modelMapperService = modelMapperService;
        this.userService = userService;
        this.cityDao = cityDao;
        this.pointService = pointService;
    }

    @Override
    public DataResult<List<RoomListDto>> getAll() {

        List<Room> result = this.roomDao.findAll();
        List<RoomListDto> response = result.stream()
                .map(room -> this.modelMapperService.forDto().map(room,RoomListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<RoomListDto>>(response,"T??m odalar listelendi.");
    }

    @Override
    public DataResult<List<RoomListDto>> getAllRoomsByCityName(String city, int userId) throws BusinessException {

        city = city.toUpperCase();
        this.userService.checkIfUserDidLogIn(userId);
        List<Room> result = this.roomDao.getAllByCity_City(city);
        List<RoomListDto> response = result.stream()
                .map(room -> this.modelMapperService.forDto().map(room,RoomListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<RoomListDto>>(response,city+" ??ehrindeki t??m odalar listelendi.");
    }


    @Override
    public DataResult<Integer> add(CreateRoomRequest createRoomRequest) throws BusinessException {

        this.userService.checkIfUserDidLogIn(createRoomRequest.getOwnerId());
        Room room = this.modelMapperService.forRequest().map(createRoomRequest,Room.class);
        createRoomRequest.setCity(createRoomRequest.getCity().toUpperCase());
        City city = this.cityDao.getCityByCity(createRoomRequest.getCity());
        room.setCity(city);
        room.setCreationDate(java.time.LocalDate.now());
        room.setAveragePoint(0);
        this.roomDao.save(room);
        List<Room> roomList = this.roomDao.getRoomByOwner_IdAndCreationDate(room.getOwner().getId(),room.getCreationDate());
        int roomId = roomList.get(roomList.size()-1).getId();


        return new SuccessDataResult<Integer>(roomId,"Oda ba??ar??l?? bir ??ekilde olu??turuldu.");
    }

    @Override
    public Result update(UpdateRoomRequest updateRoomRequest) throws BusinessException {

        this.userService.checkIfUserDidLogIn(updateRoomRequest.getOwnerId());
        Room room = this.modelMapperService.forRequest().map(updateRoomRequest,Room.class);
        this.roomDao.save(room);
        return new SuccessResult("Oda ba??ar??yla g??ncellendi.");
    }

    @Override
    public Result delete(int id) {

        this.roomDao.deleteById(id);

        return new SuccessResult("Oda ba??ar??yla silindi");
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
            throw new BusinessException("Oda bulunamad??.");
        }

        return this.roomDao.getById(roomId);
    }

    @Override
    public Room getRoomByOwner_OwnerId(int ownerId) {

        Room room = this.roomDao.getRoomByOwner_Id(ownerId);

        return room;
    }

    @Override
    public List<String> getAllParticipantsByRoomId(int roomId) {
        Room room = this.roomDao.getById(roomId);
        List<String> participants=new ArrayList<>();
        for (User user:room.getParticipants()
             ) {
            participants.add(user.getUserName());
        }

        return participants;
    }

    @Override
    public DataResult<Integer> getRoomsByOwner_Id(int ownerId) throws BusinessException {

        checkIfUserDoesHaveRoom(ownerId);
        Room room = this.roomDao.getRoomsByOwner_Id(ownerId).get(this.roomDao.getRoomsByOwner_Id(ownerId).size()-1);
        RoomByIdDto roomByIdDto = this.modelMapperService.forDto().map(room,RoomByIdDto.class);

        return new SuccessDataResult<Integer>(roomByIdDto.getId(),"Kullan??c??n??n odas?? bulundu.");
    }

    @Override
    public void save(Room room) {

        this.roomDao.save(room);
    }

    @Override
    public boolean checkIfRoomExists(int roomId){

        return this.roomDao.existsById(roomId);
    }

    private boolean checkIfUserDoesNotHaveAnyPoint(int userId){

        if (this.pointService.getAvaragePointOfHost(userId) == null || this.pointService.getAvaragePointOfHost(userId).getData() == 0){

            Room room = this.roomDao.getRoomByOwner_Id(userId);
            room.setAveragePoint(0);
        }

        return true;
    }

    private boolean checkIfUserDoesHaveRoom(int ownerId) throws BusinessException {

        if (this.roomDao.getRoomsByOwner_Id(ownerId).size() == 0){
            throw new BusinessException("Kullan??c??n??n odas?? yok!");
        }

        return true;
    }

}
