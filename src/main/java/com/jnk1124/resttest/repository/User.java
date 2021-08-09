package com.jnk1124.resttest.repository;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "사용자 상세 정보를 위한 도메인 객체")
@Entity
//@JsonIgnoreProperties(value={"password", "ssn" })
//@JsonFilter(value = "UserInfo")
public class User {

    public User(Long id, LocalDateTime dateTime, String name, String password, String ssn) {
        this.id = id;
        this.dateTime = dateTime;
        this.name = name;
        this.password = password;
        this.ssn = ssn;
    }

    @Id @GeneratedValue
    private Long id;
    @ApiModelProperty(notes = "등록일시를 입력해주세요.")
    private LocalDateTime dateTime;
    @Size(min=2, message = "이름은 2자 이상 입력해야합니다.")
    @ApiModelProperty(notes = "사용자 이름을 입력해주세요.")
    private String name;
    //@JsonIgnore
    private String password;
    //@JsonIgnore
    @ApiModelProperty(notes = "주민번호를 입력하세요.")
    private String ssn;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;
}
