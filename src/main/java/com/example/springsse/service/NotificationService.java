package com.example.springsse.service;

import com.example.springsse.controller.SseController;
import com.example.springsse.domain.Memo;
import com.example.springsse.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

import static com.example.springsse.controller.SseController.sseEmitters;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final MemoRepository memoRepository;

    public void notifyAddCommentEvent(Memo memo) {

        Long userId = memo.getUser().getId();

        if (sseEmitters.containsKey(userId)) {
            SseEmitter sseEmitter = sseEmitters.get(userId);
            try {
                sseEmitter.send(SseEmitter.event().name("addComment").data("댓글이 달렸습니다!!!!!"));
            } catch (Exception e) {
                sseEmitters.remove(userId);
            }
        }
    }
}
