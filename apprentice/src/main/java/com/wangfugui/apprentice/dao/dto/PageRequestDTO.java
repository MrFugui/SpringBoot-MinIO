package com.wangfugui.apprentice.dao.dto;

import lombok.Data;

/**
 * @author masiyi
 */
@Data
public class PageRequestDTO<T> {

    private Integer page;
    private Integer size;
    private String asc;
    private String desc;
    private T params;

}
