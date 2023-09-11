package com.sesac.reuse.service.reply;


import com.sesac.reuse.dto.reply.ReplyDTO;

public interface ReplyService {

    void register(ReplyDTO replyDTO);

    ReplyDTO read(Long id);

    void modify(ReplyDTO replyDTO);

    void remove(Long id);
}
