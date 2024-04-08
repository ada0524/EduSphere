package com.example.history.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Embeddable
public class HistoryCompositeKey implements Serializable {

    @JsonIgnore
    private Integer userId;

    private String postId;

    public HistoryCompositeKey(Integer userId, String postId) {
        this.userId = userId;
        this.postId = postId;
    }
}
