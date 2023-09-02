package com.sesac.reuse.service.board;

import com.sesac.reuse.dto.board.BoardDTO;
import com.sesac.reuse.repository.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

//@Service
@Log4j2
@RequiredArgsConstructor
public class BoareServiceImpl implements BoareService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long register(BoardDTO boardDTO) {

        //DTO -> Entity 변환
        return 1L;

    }
}
