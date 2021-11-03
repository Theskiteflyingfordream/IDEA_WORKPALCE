package com.ljw.blog.dto;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @ClassName ArticleParam
 * @Description 用于后台编辑文章和后台写文章的校验
 * @DATE 2021/10/27 11:31
 */

//Note:JSR303校验除了@Size等无法校验是否为null
public class ArticleParam {

    private Integer articleId;

    @NotNull(message = "发布的文章标题不能为空")
    @Size(min = 1, message = "发布的文章标题不能为空")
    private String articleTitle;

    @NotNull(message = "发布的文章内容不能为空")
    @Size(min = 1, message = "发布的文章内容不能为空")
    private String articleContent;

    private Integer categoryParent;

    private Integer categoryChild;

    @NotNull(message = "请填入正确的状态")
    //待解决，用正则表达式限制在0和1之间
    @Range(min = 0, max = 1, message = "请填入正确的状态")
    private Integer articleStatus;

    private String articleThumbnail;

    //Note:
    //@NotEmpty 用在集合类上面加了@NotEmpty的String类、Collection、Map、数组，是不能为null或者长度为0的(String Collection Map的isEmpty()方法)
    //@NotBlank只用于String,不能为null且trim()之后size>0
    //@NotNull:不能为null，但可以为empty,没有Size的约束
    @NotNull(message = "发布的文章的标签不能为空")
    private List<Integer> articleTagIds;

    public ArticleParam() {
    }

    public ArticleParam(Integer articleId, String articleTitle, String articleContent, Integer categoryParent, Integer articleChildCategoryId, Integer articleStatus, String articleThumbnail, List<Integer> articleTagIds) {
        this.articleId = articleId;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.categoryParent = categoryParent;
        this.categoryChild = articleChildCategoryId;
        this.articleStatus = articleStatus;
        this.articleThumbnail = articleThumbnail;
        this.articleTagIds = articleTagIds;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }

    public Integer getCategoryParent() {
        return categoryParent;
    }

    public void setCategoryParent(Integer categoryParent) {
        this.categoryParent = categoryParent;
    }

    public Integer getCategoryChild() {
        return categoryChild;
    }

    public void setCategoryChild(Integer categoryChild) {
        this.categoryChild = categoryChild;
    }

    public Integer getArticleStatus() {
        return articleStatus;
    }

    public void setArticleStatus(Integer articleStatus) {
        this.articleStatus = articleStatus;
    }

    public String getArticleThumbnail() {
        return articleThumbnail;
    }

    public void setArticleThumbnail(String articleThumbnail) {
        this.articleThumbnail = articleThumbnail;
    }

    public List<Integer> getArticleTagIds() {
        return articleTagIds;
    }

    public void setArticleTagIds(List<Integer> articleTagIds) {
        this.articleTagIds = articleTagIds;
    }
}
