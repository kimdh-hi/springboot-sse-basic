package com.example.springsse.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Memo {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String content;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(mappedBy = "memo", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public Memo(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void addComment(Comment comment) {
        this.getComments().add(comment);
        comment.setMemo(this);
    }
}
