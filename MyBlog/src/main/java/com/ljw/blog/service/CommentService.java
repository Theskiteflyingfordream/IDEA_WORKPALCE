package com.ljw.blog.service;

import com.github.pagehelper.PageInfo;
import com.ljw.blog.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * @ClassName CommentService
 * @Description TODO
 * @DATE 2021/9/19 10:09
 */
public interface CommentService {

    void insertComment(Comment comment);

    void insertCommentAndUpdateArticleCommentCount(Comment comment);

    Integer countComment();

    List<Comment> getLatestTenComment();

    List<Comment> getCommentsByCriteria(Map<String,Object> criteria);

    void deleteCommentByCommentId(Integer commentId);

    Comment getCommentByCommentId(Integer commentId);

    void deleteCommentAndUpdateArticleCommentCount(Integer commentId,Integer commentArticleId);

    void updateComment(Comment comment);

    PageInfo<Comment> getCommentsByUserWithPages(Integer pageIndex, Integer pageSize,Integer userId);
}
