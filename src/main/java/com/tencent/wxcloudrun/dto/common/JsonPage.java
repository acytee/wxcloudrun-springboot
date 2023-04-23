package com.tencent.wxcloudrun.dto.common;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * 分页处理
 *
 * @author : chenzg
 * @date : 2022-07-22 10:28:29
 **/
@Data
public class JsonPage {
    private long totalElements;
    private long totalPages;
    private long number = 1;
    private long size = 10;
    private List content;

    public JsonPage(Page<Map<String, Object>> p) {
        this.setNumber(p.getNumber() + 1);
        this.setSize(p.getSize());
        this.setTotalElements(p.getTotalElements());
        this.setTotalPages(p.getTotalPages());
        this.setContent(p.getContent());
    }

    public JsonPage(ResultList resultList, BaseBodyGet baseBodyGet) {
        long pz = baseBodyGet.getPageSize();
        long te = resultList.getCount();
        this.setNumber(baseBodyGet.getPageNumber());
        if (pz > 0) {
            this.setSize(pz);
        }
        this.setTotalElements(te);
        long tp = 0;
        if (te > 0) {
            tp = te / size;
            if (te % size != 0) {
                tp++;
            }
        }
        this.setTotalPages(tp);
        this.setContent(resultList.getData());
    }
}
