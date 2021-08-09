package com.jnk1124.resttest.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Getter @Setter
public class UserDataV2 {

    private List<User> users;

    private long maxId;

    public UserDataV2(){
        users = new ArrayList<>();
        users.add(new User( 1l, LocalDateTime.now(), "Kim", "asdf1234","12345-1592929"));
        users.add(new User(2l, LocalDateTime.now(), "Lee", "qwerty5432","113953-1938384s"));
        users.add(new User( 3l, LocalDateTime.now(), "Park", "bejimil1", "440029-128339"));

        maxId = users.size();
    }

    public long insertUser(User user){

        user.setId(++maxId);
        user.setDateTime(LocalDateTime.now());
        users.add(user);

        return maxId;
    }

}
