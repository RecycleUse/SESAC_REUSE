package com.sesac.reuse.dto.board;

import com.sesac.reuse.entity.board.BoardSearchOption;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Log4j2
public class PageRequestDTO {


    @Builder.Default
    private int page = 1;

    @Builder.Default
    private int size = 10;

    private BoardSearchOption boardSearchOption;
    private String keyword;
    private String link;

    public Pageable getPageable(String... props) {
        return PageRequest.of(this.page - 1, size, Sort.by(props).descending());
    }

    public String getLink() {
        StringBuilder builder = new StringBuilder();
        builder.append("page=" + this.page);
        builder.append("&size=" + this.size);

        if (boardSearchOption != null) {
            builder.append("&searchOption=" + this.boardSearchOption);
        }

        if (keyword != null) {
            try {
                builder.append("&keyword=" + URLEncoder.encode(keyword, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                log.error(e);
            }
            link = builder().toString();
        }
        return link;
    }

}

