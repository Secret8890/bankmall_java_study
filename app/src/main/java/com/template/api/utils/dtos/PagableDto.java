package com.template.api.utils.dtos;

import com.template.api.apps.accounts.domain.Account;
import com.template.api.jpa.base.DomainWithMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PagableDto {

    public enum OrderType {

        ASC(Sort.Direction.ASC),
        DESC(Sort.Direction.DESC);

        public final Sort.Direction dir;

        OrderType(Sort.Direction dir) {
            this.dir = dir;
        }

    }

    @Data
    public static class Request {

        @ApiModelProperty(value = "페이지", example = "0", required = false, position = 1)
        private int page = 0;

        @ApiModelProperty(value = "페이지 당 수", example = "20", required = false, position = 2)
        private int limit = 20;

        @ApiModelProperty(value = "정렬필드", example = "createdAt", required = false, position = 3)
        private String sort;

        @ApiModelProperty(value = "정렬방식", example = "DESC", required = false, position = 4)
        private OrderType order;

        public int getOffset() {
            return page * limit;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response<T> implements Streamable<T> {

        @ApiModelProperty(value = "총갯수")
        private long totalCnt;

        @ApiModelProperty(value = "페이지내 갯수")
        private long dataCnt;

        @ApiModelProperty(value = "페이지당 개수")
        private int pageSize;

        @ApiModelProperty(value = "페이지 번호")
        private int pageNum;

        private List<T> dataList;

        public static ModelMapper mapper;

        @Override
        public Iterator<T> iterator() {
            return dataList.iterator();
        }

        public static <X, Y extends DomainWithMapper> Response<X> of(Page<Y> page) {
            Response result = new Response<>();
            result.setTotalCnt(page.getTotalElements());
            result.setPageNum(page.getNumber());
            result.setPageSize(page.getSize());
            result.setDataCnt(page.getContent().stream().count());
            result.setDataList(
                    page.getContent()
                            .stream()
                            .map(v -> v.toResponse())
                            .collect(Collectors.toList())
            );

            return result;
        }

        public static <X, Y, Z> Response<X> of(Page<Y> page, List<Z> dataList) {
            Response result = new Response<>();
            result.setTotalCnt(page.getTotalElements());
            result.setPageNum(page.getNumber());
            result.setPageSize(page.getSize());
            result.setDataCnt(dataList.stream().count());
            result.setDataList(dataList);

            return result;
        }

//        public static <X, Y> Response<X> of(long totalCnt, int pageNum, int pageSize, List<Y> dataList) {
//            Response result = new Response<>();
//            result.setTotalCnt(totalCnt);
//            result.setPageNum(pageNum);
//            result.setPageSize(pageSize);
//            result.setDataList(dataList);
//
//            return result;
//        }
    }


    public static <T> Page<T> getPage(List<T> content, Pageable pageable, long total){
        return new PageImpl<T>(content, pageable, total);
    }
}
