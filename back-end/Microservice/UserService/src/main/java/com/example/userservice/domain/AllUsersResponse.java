package com.example.userservice.domain;

import com.example.userservice.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllUsersResponse {
    private List<User> users;
}
