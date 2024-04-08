package com.example.history.entity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="history")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
public class History {

    @EmbeddedId
    private HistoryCompositeKey id;

    @Column
    private LocalDateTime viewDate;

    public History(HistoryCompositeKey id, LocalDateTime viewDate) {
        this.id = id;
        this.viewDate = viewDate;
    }
}
