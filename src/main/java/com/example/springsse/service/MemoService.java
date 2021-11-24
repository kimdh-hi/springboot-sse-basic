package com.example.springsse.service;

import com.example.springsse.domain.Comment;
import com.example.springsse.domain.Memo;
import com.example.springsse.domain.User;
import com.example.springsse.dto.CommentDto;
import com.example.springsse.dto.MemoDetailDto;
import com.example.springsse.dto.MemoDto;
import com.example.springsse.repository.CommentRepository;
import com.example.springsse.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void saveMemo(MemoDto memoDto, User user) {
        memoRepository.save(new Memo(memoDto.getTitle(), memoDto.getContent(), user));
    }

    @Transactional(readOnly = true)
    public List<MemoDto> findAllMemo() {
        return memoRepository.findAll().stream()
                .map(m -> new MemoDto(m.getId(), m.getTitle(), m.getContent())).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Memo findMemo(Long id) {
        return memoRepository.getMemoDetail(id);


    }

    @Transactional
    public Memo addComment(Long memoId, User user, CommentDto commentDto) {
        Memo memo = memoRepository.findById(memoId).get();

        Comment comment = new Comment(commentDto.getContent(), user, memo);
        memo.addComment(comment);

        return memo;
    }
}
