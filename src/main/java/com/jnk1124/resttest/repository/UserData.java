package com.jnk1124.resttest.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter @Setter
public class UserData {

    private List<User> users;

    private long maxId;

    public UserData(){
        users = new ArrayList<>();
        users.add(new User("Kim", 1, LocalDateTime.now()));
        users.add(new User("Lee", 2, LocalDateTime.now()));
        users.add(new User("Park", 3, LocalDateTime.now()));

        maxId = users.size();
    }

    public long insertUser(User user){

        user.setId(++maxId);
        user.setDateTime(LocalDateTime.now());
        users.add(user);

        return maxId;
    }

}
