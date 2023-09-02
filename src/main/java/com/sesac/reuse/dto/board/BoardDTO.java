//package com.sesac.reuse.dto.board;
//
//
//import com.sesac.reuse.base.BaseEntity;
//import com.sesac.reuse.entity.board.Board;
//import com.sesac.reuse.entity.board.Status;
//import com.sesac.reuse.entity.board.Type;
//import com.sesac.reuse.entity.member.Member;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import javax.validation.constraints.NotEmpty;
//
//@Builder
//@Getter
//@Service
//@NoArgsConstructor
//@AllArgsConstructor
//public class BoardDTO  {
//
//    private Long boardId;
//
//    private Type type;
//
//    @NotEmpty
//    private String title;
//    @NotEmpty
//    private String content;
//    @NotEmpty
//    private Member writer; // DTO<-> Entity 변환시 setter로 직접 주입하는 주의 필요!
//    private Status status;
//
////    public static Board toEntity(BoardDTO boardDTO, Member member) {
////        new
////    }
//}
