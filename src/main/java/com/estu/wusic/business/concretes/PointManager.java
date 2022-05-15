package com.estu.wusic.business.concretes;

import com.estu.wusic.business.abstracts.PointService;
import com.estu.wusic.business.abstracts.RoomService;
import com.estu.wusic.business.abstracts.UserService;
import com.estu.wusic.business.dtos.pointDtos.PointByIdDto;
import com.estu.wusic.business.dtos.pointDtos.PointListDto;
import com.estu.wusic.business.requests.pointRequests.CreatePointRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.mapping.ModelMapperService;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.core.utilities.results.SuccessDataResult;
import com.estu.wusic.core.utilities.results.SuccessResult;
import com.estu.wusic.dataAccess.abstracts.PointDao;
import com.estu.wusic.entities.Point;
import com.estu.wusic.entities.Room;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PointManager implements PointService {

    private PointDao pointDao;
    private ModelMapperService modelMapperService;
    private RoomService roomService;

    private UserService userService;

    public PointManager(PointDao pointDao, ModelMapperService modelMapperService, RoomService roomService,
                        UserService userService) {
        this.pointDao = pointDao;
        this.modelMapperService = modelMapperService;
        this.roomService = roomService;
        this.userService = userService;
    }

    @Override
    @Transactional
    public Result add(CreatePointRequest createPointRequest) throws BusinessException {

        Point point = this.modelMapperService.forRequest().map(createPointRequest,Point.class);

        checkIfUserIsInTheRoom(createPointRequest.getPointsOwnerId(),point);

        this.pointDao.save(point);

        Room room = this.roomService.getRoomByOwner_OwnerId(createPointRequest.getPointsRecieverId());
        room.setAveragePoint(getAvaragePointOfHost(createPointRequest.getPointsRecieverId()).getData());
        this.roomService.save(room);

        return new SuccessResult("Puan başarıyla verildi.");
    }

    @Override
    public DataResult<PointByIdDto> getPointByPointId(int id) {

        Point result = this.pointDao.getById(id);
        PointByIdDto response = this.modelMapperService.forDto().map(result,PointByIdDto.class);

        return new SuccessDataResult<PointByIdDto>(response,"Puan id kulllanarak bulundu.");
    }

    public DataResult<List<PointListDto>> getAllByPointsRecieverId(int id) {

        List<Point> points = this.pointDao.getAllByPointsRecieverId(id);
        List<PointListDto> result = points.stream()
                .map(point->this.modelMapperService.forDto().map(point,PointListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<PointListDto>>(result,"Kullanıcının aldığı puanlar listelendi.");
    }

    @Override
    public DataResult<Double> getAvaragePointOfHost(int userId) {

        double result=0;

        for (Point p:this.pointDao.getAllByPointsRecieverId(userId)
             ) {
            result += p.getGivenPoint();
        }

        result /= this.pointDao.getAllByPointsRecieverId(userId).size();

        return new SuccessDataResult<Double>(result,"Kullanıcın ortalama puanı başarıyla hesaplandı.");
    }

    @Override
    public void save(Point point) {

        this.pointDao.save(point);
    }

    public boolean checkIfUserIsInTheRoom(int ownerId, Point point) throws BusinessException {

        if (this.userService.getUserById(ownerId).getRoomJoined() !=
                this.roomService.getRoomByOwner_OwnerId(point.getPointsReciever().getId())){
            throw new BusinessException("Bu odada katılımcı değilsiniz");
        }

        return true;
    }
}
