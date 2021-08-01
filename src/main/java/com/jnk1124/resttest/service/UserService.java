package com.jnk1124.resttest.service;

import com.jnk1124.resttest.controller.UserController;
import com.jnk1124.resttest.repository.User;
import com.jnk1124.resttest.repository.UserData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserData userData;

    public List<User> getUserList() {
        return userData.getUsers();
    }

    public User getUser(long userId) {

        for(User user : userData.getUsers()){
            if(user.getId() == userId){
                return user;
            }
        }
        return null;
    }

    public long insertUser(User user){
        long maxId = userData.insertUser(user);

        return maxId;
    }

    public User deleteById(long id){

        Iterator<User> userIterator = userData.getUsers().iterator();

        while(userIterator.hasNext()){
            User user = userIterator.next();
            if(user.getId() == id){
                userIterator.remove();
                return user;
            }
        }

        return null;
    }

    public User updateUser(User updateUser){
        long id = updateUser.getId();

        logger.debug("update Id : " + id);

        User user = getUser(id);
        user.setName(updateUser.getName());
        user.setDateTime(LocalDateTime.now());

        return user;
    }
}
