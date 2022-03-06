package com.estu.wusic.api.controllers;

import com.estu.wusic.business.abstracts.UserService;
import com.estu.wusic.business.dtos.UserByIdDto;
import com.estu.wusic.business.dtos.UserListDto;
import com.estu.wusic.business.requests.CreateUserRequest;
import com.estu.wusic.business.requests.UpdateUserRequest;
import com.estu.wusic.core.utilities.results.DataResult;
import com.estu.wusic.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addUser")
    public Result add(@RequestBody CreateUserRequest createUserRequest){
        return this.userService.add(createUserRequest);
    }

    @PostMapping("/updateUser")
    public Result update(@RequestBody UpdateUserRequest updateUserRequest){
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
}
