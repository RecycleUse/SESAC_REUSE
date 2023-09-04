package com.sesac.reuse.controller;

import com.sesac.reuse.dto.reply.ReplyDTO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController // CustomRestAdvice 도 같이 보기
@RequestMapping("/replies")
@Log4j2
public class ReplyController {

    //ResponseEntity 응답으로 상태코드 전달
    @ApiOperation(value = "Replies POST", notes="POST 방식으로 댓글 등록")
    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Long>> register(@RequestBody @Valid ReplyDTO replyDTO, BindingResult bindingResult) throws BindException {
        log.info(replyDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        //임시
        Map<String, Long> resultMap = Map.of("rno", 11L);
        return ResponseEntity.ok(resultMap);
    }
}
