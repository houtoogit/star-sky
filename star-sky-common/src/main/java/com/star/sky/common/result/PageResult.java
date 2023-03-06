package com.star.sky.common.result;

import com.github.pagehelper.PageInfo;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PageResult<T> {

    private int page;
    private int pageSize;
    private int totalPage;
    private long total;
    private List<T> list;

    // 将PageHelper分页后的list转为分页信息
    public static <T> PageResult<T> page(List<T> list) {
        PageResult<T> result = new PageResult<T>();
        PageInfo<T> pageInfo = new PageInfo<T>(list);
        result.setTotalPage(pageInfo.getPages());
        result.setPage(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setList(pageInfo.getList());
        return result;
    }

    // 将SpringData分页后的list转为分页信息
    public static <T> PageResult<T> page(Page<T> pageInfo) {
        PageResult<T> result = new PageResult<T>();
        result.setTotalPage(pageInfo.getTotalPages());
        result.setPage(pageInfo.getNumber());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotalElements());
        result.setList(pageInfo.getContent());
        return result;
    }

}
