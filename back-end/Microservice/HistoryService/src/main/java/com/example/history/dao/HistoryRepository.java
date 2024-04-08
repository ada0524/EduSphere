package com.example.history.dao;

import com.example.history.entity.History;
import com.example.history.entity.HistoryCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, HistoryCompositeKey> {

    List<History> findAllByIdUserId(Integer userId);

}
