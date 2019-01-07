package com.jiazhuo.blockgamesquare.qo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter@Setter
public class PageResult extends QueryObject {

    //具体的数据
    private List data;
    //总数据量
    private Integer totalCount;
    //总页数
    private Integer totalPage;
    //上一页
    private Integer prevPage;
    //下一页
    private Integer nextPage;

    public PageResult(){}

    /**
     *
     * @param data
     * @param totalCount
     * @param currentPage
     * @param pageSize
     */
    public PageResult(List data,Integer totalCount,Integer currentPage,Integer pageSize){
        this.data = data;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        if (pageSize >= totalCount){
            totalPage = 1;
            prevPage = 1;
            nextPage = 1;
            return;
        }
        totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        prevPage = currentPage > 1 ? currentPage - 1 : 1;
        nextPage = currentPage < totalPage ? currentPage + 1 : totalPage;

    }

    public static PageResult empty(){
        return new PageResult(Collections.emptyList(),0,1,1);
    }
}
