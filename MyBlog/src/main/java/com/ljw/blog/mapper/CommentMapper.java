package com.ljw.blog.mapper;

import com.ljw.blog.entity.Comment;

import java.util.List;
import java.util.Map;

public interface CommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insertAutoId(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    List<Comment> selectAll();

    int updateByPrimaryKey(Comment record);

    int countComment();

    List<Comment> getLatestTenComment();

    List<Comment> selectCommentsByCriteria(Map<String,Object> criteria);

}