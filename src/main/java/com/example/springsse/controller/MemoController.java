package com.example.springsse.controller;

import com.example.springsse.domain.Memo;
import com.example.springsse.dto.CommentDto;
import com.example.springsse.dto.MemoDetailDto;
import com.example.springsse.dto.MemoDto;
import com.example.springsse.security.UserDetailsImpl;
import com.example.springsse.service.MemoService;
import com.example.springsse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;
    private final NotificationService notificationService;

    @PostMapping("/memo")
    public ResponseEntity postMemo(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody MemoDto memoDto
    ) {
        memoService.saveMemo(memoDto, userDetails.getUser());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/memos")
    public ResponseEntity<List<MemoDto>> getAllMemos() {
        List<MemoDto> memos = memoService.findAllMemo();
        return ResponseEntity.ok(memos);
    }

    @GetMapping("/memo")
    public Memo getMemo(@RequestParam Long id) {
        log.info("getMemo id = {}", id);
        return memoService.findMemo(id);
    }

    @PostMapping("/memo/{id}/comment")
    public ResponseEntity addComment(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long id,
            @RequestBody CommentDto commentDto) {
        Memo memo = memoService.addComment(id, userDetails.getUser(), commentDto);
        notificationService.notifyAddCommentEvent(memo);
        return ResponseEntity.ok("ok");
    }
}
