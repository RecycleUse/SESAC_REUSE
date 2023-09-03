package com.sesac.reuse.dto.board;

import com.sesac.reuse.entity.board.Type;
import com.sesac.reuse.repository.search.SearchOption;
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

    private SearchOption searchOption;
    private String keyword;
    private String link;

    //Pageable 객체를 사용해야하므로 => Repository에서 찾을 때 인자로
    public Pageable getPageable(String... props) {
        return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
    }

    public String getLink() { //검색조건 쿼파
        StringBuilder builder = new StringBuilder();
        builder.append("page=" + this.page);
        builder.append("&size=" + this.size);

        if (searchOption != null) {
            builder.append("&searchOption=" + this.searchOption);
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
