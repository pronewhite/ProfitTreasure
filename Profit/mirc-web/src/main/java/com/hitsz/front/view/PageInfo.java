package com.hitsz.front.view;

/**
 * 分页信息
 */
public class PageInfo {

    //页号
    private Integer pageNo;
    //每页显示数量
    private Integer pageSize;
    //总页数
    private Integer totaoPages;
    //总记录数，用来计算页数
    private Integer sumRecords;

    public PageInfo() {
    }

    public PageInfo(Integer pageNo, Integer pageSize, Integer sumRecords) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.sumRecords = sumRecords;

        //计算总页数
        if( this.sumRecords % this.pageSize  == 0 ){
            this.totaoPages = this.sumRecords / this.pageSize;
        } else {
            this.totaoPages = this.sumRecords / this.pageSize + 1;
        }
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotaoPages() {
        return totaoPages;
    }

    public void setTotaoPages(Integer totaoPages) {
        this.totaoPages = totaoPages;
    }

    public Integer getSumRecords() {
        return sumRecords;
    }

    public void setSumRecords(Integer sumRecords) {
        this.sumRecords = sumRecords;
    }
}
