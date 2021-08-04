package com.jnk1124.resttest.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jnk1124.resttest.exception.UserNotFoundException;
import com.jnk1124.resttest.repository.User;
import com.jnk1124.resttest.repository.UserV2;
import com.jnk1124.resttest.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {

    private static Logger logger = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public MappingJacksonValue getUserList(){
        List<User> userList = userService.getUserList();

        SimpleBeanPropertyFilter filter
                = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "dateTime", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userList);

        mapping.setFilters(filters);

        return mapping;
    }

   // @GetMapping("/v1/users/{userId}")
    //@GetMapping(value = "/users/{userId}/", params = "version=1")
    //@GetMapping(value = "/users/{userId}", headers="X-API-VERSION=1")
   @GetMapping(value = "/users/{userId}", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue getUsersV1(@PathVariable long userId){

        User selectUser = userService.getUser(userId);

        if(selectUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        SimpleBeanPropertyFilter filter
                = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "dateTime", "ssn");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(selectUser);

        mapping.setFilters(filters);

        return mapping;
    }

    //일반 브라우저에서 실행가능
    //URI Path이용
    //@GetMapping("/v2/users/{userId}")
    //parameter이용
    //@GetMapping(value = "/users/{userId}/", params = "version=2")

    //일반 브라우저에서 실행불가
    //Header값이용
    //@GetMapping(value = "/users/{userId}", headers="X-API-VERSION=2")
    //MimeType이용
    @GetMapping(value = "/users/{userId}", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue getUsersV2(@PathVariable long userId){

        User selectUser = userService.getUser(userId);

        if(selectUser == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(selectUser, userV2);
        userV2.setGrade("VIP");

        SimpleBeanPropertyFilter filter
                = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "dateTime", "grade");

        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);

        mapping.setFilters(filters);

        return mapping;
    }
}
