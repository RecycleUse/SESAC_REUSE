package com.sesac.reuse.service.Board;

import com.sesac.reuse.dto.board.BoardListReplyCountDTO;
import com.sesac.reuse.dto.board.PageRequestDTO;
import com.sesac.reuse.dto.board.PageResponseDTO;
import com.sesac.reuse.entity.board.Board;
import com.sesac.reuse.entity.board.BoardDTO;
import com.sesac.reuse.entity.board.Status;
import com.sesac.reuse.entity.board.Type;
import com.sesac.reuse.entity.member.Member;
import com.sesac.reuse.repository.board.BoardRepository;
import com.sesac.reuse.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Log4j2
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @PreAuthorize("isAuthenticated()")
    @Override
    public Long register(BoardDTO boardDTO, String email) throws Exception {

        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원을 찾지 못했습니다."));
        boardDTO.setNickname(member.getNickname());
        Board board = modelMapper.map(boardDTO, Board.class);

        if(board.getType()== Type.NOTICE) {
            board.setStatus(Status.COMPLETED);
        }

        board.setMember(member);

        try {

            return boardRepository.save(board).getBoardId();
        } catch (IllegalArgumentException e) {
            log.error("게시글 저장 중 오류 발생");
            throw new Exception("arguments error 발생", e);
        } catch (Exception e) {
            log.error(e);
            throw new Exception();
        }

    }

    @Override
    public BoardDTO readOne(Long id) {

        Optional<Board> result = boardRepository.findById(id);
        Board board = result.orElseThrow();

        BoardDTO boardDTO = modelMapper.map(board, BoardDTO.class);
        log.info("boardDTO={}", boardDTO);

        return boardDTO;
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBoardId());

        try {
            Board board = result.orElseThrow();
            board.change(boardDTO.getType(), boardDTO.getTitle(), boardDTO.getContent());

            boardRepository.save(board);
        } catch (NoSuchElementException e) {
            log.error(e);
        } catch (AccessDeniedException e) {
            log.error(e);
            throw new AccessDeniedException("권한이 없습니다.");
        }


    }

    @Override
    public void remove(Long id) {
        boardRepository.deleteById(id);
    }


    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithReplyCount(PageRequestDTO pageRequestDTO) {

        Page<BoardListReplyCountDTO> result =
                boardRepository.searchWithReplyCount(pageRequestDTO.getBoardSearchOption(), pageRequestDTO.getKeyword(), pageRequestDTO.getPageable("regDate"));

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();

    }


}