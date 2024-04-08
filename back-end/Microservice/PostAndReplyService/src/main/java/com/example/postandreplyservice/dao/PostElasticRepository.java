package com.example.postandreplyservice.dao;

import com.example.postandreplyservice.entity.PostElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostElasticRepository extends ElasticsearchRepository<PostElastic, String> {
    List<PostElastic> findByTitleContainingOrContentContaining(String titleKeyword, String contentKeyword);
}
