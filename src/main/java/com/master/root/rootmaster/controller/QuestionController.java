package com.master.root.rootmaster.controller;

import com.master.root.rootmaster.exception.BadRequestException;
import com.master.root.rootmaster.models.Player;
import com.master.root.rootmaster.models.Room;
import com.master.root.rootmaster.models.dto.AnswerDto;
import com.master.root.rootmaster.models.enums.PlayerLastResult;
import com.master.root.rootmaster.models.enums.PlayerState;
import com.master.root.rootmaster.service.QuestionService;
import com.master.root.rootmaster.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    private final RoomService roomService;


    @PostMapping("/answer")
    public ResponseEntity<?> answer(@RequestBody AnswerDto answerDto) {
        Room room = roomService.getRoom(answerDto.roomId());
        return room.questions()
                .stream()
                .filter(question -> question.id().equals(answerDto.questionId()))
                .findFirst()
                .map(question -> room.players()
                        .stream()
                        .filter(it -> it.getUserName().equals(answerDto.userName()))
                        .findFirst()
                        .map(it -> {
                            it.setState(PlayerState.PREPARING);
                            if (question.answerIndex().equals(answerDto.answerIndex())) {
                                it.setLastResult(PlayerLastResult.SUCCEED);
                                it.addScore(10000 / answerDto.time());
                            } else {
                                it.setLastResult(PlayerLastResult.FAILED);
                            }
                            return it;
                        }))
                .map(Optional::get)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new BadRequestException("PLayer not found"));
    }
}
