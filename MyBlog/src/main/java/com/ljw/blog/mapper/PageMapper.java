package com.ljw.blog.mapper;

import com.ljw.blog.entity.Page;

import java.util.List;

public interface PageMapper {
    int deleteByPrimaryKey(Integer pageId);

    int insert(Page record);

    Page selectByPrimaryKey(Integer pageId);

    List<Page> selectAll();

    int updateByPrimaryKey(Page record);
}