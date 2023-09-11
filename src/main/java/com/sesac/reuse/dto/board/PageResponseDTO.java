package com.sesac.reuse.dto.board;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PageResponseDTO<E> {

    private int page = 1;

    private int size = 10;
    private int total;
    private int start;
    private int end;
    private int last;
    private boolean prev;
    private boolean next;
    private List<E> dtoList;

    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {

        if(total <= 0) return;

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total = total;
        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page / 10.0)) * 10;
        this.start = this.end - 9;

        this.last = (int)Math.ceil(total / (double)this.size);

        this.end = Math.min(end,last);

        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }

}
