package com.jnk1124.resttest.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class User {
    private String name;
    private long id;
    private LocalDateTime dateTime;
}
