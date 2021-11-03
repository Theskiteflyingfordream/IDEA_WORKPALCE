package com.ljw.blog.mapper;

import com.ljw.blog.entity.Link;
import java.util.List;

public interface LinkMapper {
    int deleteByPrimaryKey(Integer linkId);

    int insert(Link record);

    Link selectByPrimaryKey(Integer linkId);

    List<Link> selectAll();

    int updateByPrimaryKey(Link record);
}