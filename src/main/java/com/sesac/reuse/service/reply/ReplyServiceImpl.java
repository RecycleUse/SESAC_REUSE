package com.sesac.reuse.service.reply;

import com.sesac.reuse.dto.reply.ReplyDTO;
import com.sesac.reuse.entity.reply.Reply;
import com.sesac.reuse.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;
    @Override
    public void register(ReplyDTO replyDTO) {

        Reply reply = modelMapper.map(replyDTO, Reply.class);
        replyRepository.save(reply);

    }

    @Override
    public ReplyDTO read(Long replyId) {

        Optional<Reply> optionalReply = replyRepository.findById(replyId);

        Reply reply = optionalReply.orElseThrow();
        return modelMapper.map(reply, ReplyDTO.class);

    }

    @Override
    public void modify(ReplyDTO replyDTO) {

        Optional<Reply> optionalReply = replyRepository.findById(replyDTO.getReplyId());
        Reply reply = optionalReply.orElseThrow();
        reply.changeContent(replyDTO.getContent());
        replyRepository.save(reply);

    }

    @Override
    public void remove(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
