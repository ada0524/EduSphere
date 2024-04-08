package com.example.history.service;

import com.example.history.dao.HistoryRepository;
import com.example.history.domain.Post;
import com.example.history.dto.HistoryPost;
import com.example.history.entity.History;
import com.example.history.entity.HistoryCompositeKey;
import com.example.history.service.remote.RemotePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class HistoryService {
    @Autowired
    private HistoryRepository historyRepository;

    private RemotePostService remotePostService;

    @Autowired
    public void setRemotePostService(RemotePostService remotePostService) {
        this.remotePostService = remotePostService;
    }

    @Transactional
    public List<HistoryPost> getHistoryPostsByUser(Integer userId, String token){
        List<History> allByIdUserId = this.historyRepository.findAllByIdUserId(userId);
        allByIdUserId.sort(Comparator.comparing(History::getViewDate));
        Collections.reverse(allByIdUserId);

        ArrayList<HistoryPost> posts = new ArrayList<>();

        for (History history : allByIdUserId) {
            Post postById = remotePostService.getPostById(history.getId().getPostId(), token);
            if (postById.getStatus().equals("PUBLISHED")) {
                HistoryPost historyPost = HistoryPost.builder().postId(postById.getPostId()).content(postById.getContent()).viewTime(history.getViewDate()).title(postById.getTitle()).build();
                posts.add(historyPost);
            }
        }
        return posts;
    }

    @Transactional
    public List<HistoryPost> getHistoryPostsByUserWithKeyWord(Integer userId, String token, String keyWord){
        List<History> allByIdUserId = this.historyRepository.findAllByIdUserId(userId);
        allByIdUserId.sort(Comparator.comparing(History::getViewDate));
        Collections.reverse(allByIdUserId);

        ArrayList<HistoryPost> posts = new ArrayList<>();

        for (History history : allByIdUserId) {
            Post postById = remotePostService.getPostById(history.getId().getPostId(), token);
            if (postById.getStatus().equals("PUBLISHED") && postById.getContent().toLowerCase().contains(keyWord.toLowerCase()) && postById.getTitle().toLowerCase().contains(keyWord.toLowerCase())) {
                HistoryPost historyPost = HistoryPost.builder().postId(postById.getPostId()).content(postById.getContent()).viewTime(history.getViewDate()).title(postById.getTitle()).build();
                posts.add(historyPost);
            }
        }
        return posts;
    }

    @Transactional
    public void putHistory(Integer userId, String postId){
        this.historyRepository.save(new History(new HistoryCompositeKey(userId, postId), LocalDateTime.now()));
    }

    @Transactional
    public List<History> getAll(){
        return this.historyRepository.findAll();
    }


}
