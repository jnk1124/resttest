package com.jnk1124.resttest.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class User {

    @Size(min=2, message = "이름은 2자 이상 입력해야합니다.")
    private String name;
    private long id;
    private LocalDateTime dateTime;
}
