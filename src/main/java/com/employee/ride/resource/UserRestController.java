package com.employee.ride.resource;

import com.employee.ride.common.constant.UserConstant;
import com.employee.ride.entity.User;
import com.employee.ride.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UserConstant.USER_URL_BASE)
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(UserConstant.USERS)
    public ResponseEntity<List<User>> getUsers() {
        List<User> listOfUser = userService.getListOfUser();
        if (!CollectionUtils.isEmpty(listOfUser)) {
            return new ResponseEntity<>(listOfUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
