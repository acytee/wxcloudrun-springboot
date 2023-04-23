package com.tencent.wxcloudrun.dto.common;

import lombok.Data;

import java.util.List;

/**
 * 列表信息
 *
 * @author : chenzg
 * @date : 2022-07-19 17:18:42
 **/
@Data
public class ResultList {
    private int count;
    private List data;

    public ResultList() {
    }

    public ResultList(int count, List data) {
        this.count = count;
        this.data = data;
    }
}
