package com.example.springsse.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue
    private Long id;

    private String content;

    @JsonIgnore
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @JoinColumn(name = "memo_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Memo memo;

    public void setMemo(Memo memo) {
        this.memo = memo;
    }

    public Comment(String content, User user, Memo memo) {
        this.content = content;
        this.user = user;
        this.memo = memo;
    }
}
