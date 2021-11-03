package com.ljw.blog.mapper;

import com.ljw.blog.entity.Tag;
import java.util.List;

public interface TagMapper {
    int deleteByPrimaryKey(Integer tagId);

    int insert(Tag record);

    Tag selectByPrimaryKey(Integer tagId);

    List<Tag> selectAll();

    int updateByPrimaryKey(Tag record);

    List<Tag> selectArticleTags(Integer articleId);
}