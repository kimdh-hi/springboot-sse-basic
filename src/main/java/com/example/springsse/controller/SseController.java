package com.example.springsse.controller;

import com.example.springsse.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@Slf4j
@RestController
public class SseController {

    private List<SseEmitter> sseEmitters = new CopyOnWriteArrayList<>();

    @CrossOrigin
    @GetMapping(value = "/sub", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe() {
        // SseEmitter 생성
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sseEmitter.onCompletion(() -> sseEmitters.remove(sseEmitter));

        sseEmitters.add(sseEmitter);

        return sseEmitter;
    }

    @PostMapping("/dispatch")
    public void dispatch(@RequestBody MessageDto message) {
        for (SseEmitter sseEmitter : sseEmitters) {
            try {
                // 파라미터로 받은 message를 "message"라는 이름으로 이벤트 발행
                sseEmitter.send(SseEmitter.event().name("message").data(message));
            } catch (IOException e) {
                sseEmitters.remove(sseEmitter);
            }
        }
    }
}
