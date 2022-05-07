package com.estu.wusic.api.controllers;

import com.estu.wusic.business.abstracts.UserService;
import com.estu.wusic.business.dtos.userDtos.UserByIdDto;
import com.estu.wusic.business.dtos.userDtos.UserListDto;
import com.estu.wusic.business.requests.userRequests.CreateUserRequest;
import com.estu.wusic.business.requests.userRequests.UpdateUserRequest;
import com.estu.wusic.core.exceptions.BusinessException;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import com.estu.wusic.entities.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(name = "/api/users")
public class UsersController {

    private UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAll")
    public DataResult<List<UserListDto>> getAll(){
        return this.userService.getAll();
    }

    @PostMapping("/register")
    public Result add(@RequestBody @Valid CreateUserRequest createUserRequest){
        return this.userService.add(createUserRequest);
    }

    @PostMapping("/updateUser")
    public Result update(@RequestBody @Valid UpdateUserRequest updateUserRequest){
        return this.userService.update(updateUserRequest);
    }

    @GetMapping("/getUserByUserId")
    public DataResult<UserByIdDto> getUserByUserId(@RequestParam int id){
        return this.userService.getUserByUserId(id);
    }

    @DeleteMapping("/deleteUserByUserId")
    public Result deleteUserByUserId(@RequestParam int id){
        return this.userService.deleteUserByUserId(id);
    }

    @GetMapping("/getAllUsersPaged")
    public DataResult<List<UserListDto>> getAllUsersPaged(@RequestParam int pageNo,@RequestParam int pageSize){
        return this.userService.getAllUsersPaged(pageNo,pageSize);
    }

    @GetMapping("/getAllUsersSorted")
    public DataResult<List<UserListDto>> getAllUsersSorted(@RequestParam String ascOrDesc){
        return this.userService.getAllUsersSorted(ascOrDesc);
    }

    @GetMapping("/login")
    public Result login(@RequestParam String userName, @RequestParam String password) throws BusinessException {

        return this.userService.login(userName,password);
    }

    @GetMapping("/joinIntoRoom")
    public Result joinIntoRoom(@RequestParam int userId, @RequestParam int roomId) throws BusinessException {

        return this.userService.joinIntoRoom(userId,roomId);
    }

    @GetMapping("/leaveFromRoom")
    public Result leaveFromRoom(@RequestBody int userId) throws BusinessException {

        return this.userService.leaveFromRoom(userId);
    }

}
