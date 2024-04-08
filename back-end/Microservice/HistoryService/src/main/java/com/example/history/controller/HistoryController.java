package com.example.history.controller;

import com.example.history.dto.DataResponse;
import com.example.history.dto.HistoryPost;
import com.example.history.exceptions.UnauthenticatedException;
import com.example.history.secutiry.JwtPayload;
import com.example.history.secutiry.JwtTokenProvider;
import com.example.history.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {


    @Autowired
    private HistoryService historyService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @GetMapping("/users/{userId}/view-history")
    public DataResponse getUserHistory(@PathVariable Integer userId, @RequestHeader("Authorization") String token, @RequestParam(required = false) String keyWord){

        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Integer jwt_userId = Math.toIntExact(payload.getUserId());
        System.out.println(jwt_userId);
        System.out.println(userId);

//        if (!userType.equals("ADMIN_USER") && userId != jwt_userId) {
//            throw new UnauthenticatedException("Not Authorized");
//        }

        if (keyWord == null) {
            List<HistoryPost> allHistoryPosts = this.historyService.getHistoryPostsByUser(userId, token);
            return DataResponse.builder().success(true)
                    .data(allHistoryPosts).build();
        } else {
            List<HistoryPost> allHistoryPosts = this.historyService.getHistoryPostsByUserWithKeyWord(userId, token, keyWord);
            return DataResponse.builder().success(true)
                    .data(allHistoryPosts).build();
        }
    }

    @PutMapping ("/view-history/{userId}")
    public DataResponse updateHistory(@PathVariable Integer userId, @RequestParam String postId, @RequestHeader("Authorization") String token) {
        JwtPayload payload = jwtTokenProvider.parseToken(token);
        String userType = payload.getUserType();
        Integer jwt_userId = Math.toIntExact(payload.getUserId());

//        if (userId != jwt_userId) {
//            throw new UnauthenticatedException("Not Authorized");
//        }

        this.historyService.putHistory(userId, postId);
        return DataResponse.builder().success(true).build();
    }

}
