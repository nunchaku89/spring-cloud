package com.example.authserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(name = "user_test")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "user_name")
    private String userName;
    private String userPassword;
    private String userRole;
    private Timestamp createDate;

    public User(String userId, String userName, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public User(String userId, String userName, String userPassword, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<String> getRoles() {
        if (this.userRole.length() > 0) {
            return Arrays.asList(this.userRole.split(","));
        }

        return new ArrayList<>();
    }
}
