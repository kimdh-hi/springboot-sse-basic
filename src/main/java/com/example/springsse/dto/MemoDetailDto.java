package com.example.springsse.dto;

import com.example.springsse.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class MemoDetailDto {

    private Long memoId;
    private String title;
    private String content;
    private List<CommentDto> comments = new ArrayList<>();
}
