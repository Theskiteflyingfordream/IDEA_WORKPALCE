package com.ljw.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ljw.blog.entity.Article;
import com.ljw.blog.entity.Comment;
import com.ljw.blog.mapper.CommentMapper;
import com.ljw.blog.service.ArticleService;
import com.ljw.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CommentServiceImpl
 * @Description TODO
 * @DATE 2021/9/19 12:47
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleService articleService;

    @Override
    public void insertComment(Comment comment){
        commentMapper.insertAutoId(comment);
    }

    @Override
    public void insertCommentAndUpdateArticleCommentCount(Comment comment) {
        this.insertComment(comment);
        articleService.updateArticleCommentCount(comment.getCommentArticleId());
    }

    @Override
    public Integer countComment(){
        return commentMapper.countComment();
    }

    @Override
    public List<Comment> getLatestTenComment(){
        return commentMapper.getLatestTenComment();
    }

    @Override
    public List<Comment> getCommentsByCriteria(Map<String,Object> criteria){
        return commentMapper.selectCommentsByCriteria(criteria);
    }

    @Override
    public void deleteCommentByCommentId(Integer commentId){
        commentMapper.deleteByPrimaryKey(commentId);
    }

    @Override
    public Comment getCommentByCommentId(Integer commentId){
        return commentMapper.selectByPrimaryKey(commentId);
    }

    //连子评论一起删除
    @Override
    public void deleteCommentAndUpdateArticleCommentCount(Integer commentId,Integer commentArticleId) {
        this.deleteCommentByCommentId(commentId);
        articleService.updateArticleCommentCount(commentArticleId);
    }

    @Override
    public void updateComment(Comment comment){
        commentMapper.updateByPrimaryKey(comment);
    }

    @Override
    public PageInfo<Comment> getCommentsByUserWithPages(Integer pageIndex, Integer pageSize,Integer userId){
        PageHelper.startPage(pageIndex,pageSize);
        Map<String,Object> criteria = new HashMap<>();
        criteria.put("userID",userId);
        List<Comment> commentList = commentMapper.selectCommentsByCriteria(criteria);
        return new PageInfo<>(commentList);
    }

}
