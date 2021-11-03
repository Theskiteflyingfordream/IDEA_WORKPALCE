function increaseViewCountOrLikeCount(prj) {
    /*prj为0代表点赞，为1代表浏览量*/
    var element;
    var key;
    if(prj==0){
        var prefixUrl = "/article/like/"
        element = "#articleLikeCount"
        key = "likeId";
    }else{
        var prefixUrl = "/article/view/"
        element = "#articleViewCount"
        key = "viewId";
    }
    if($.cookie(key)!=articleId || $.cookie(key)==null){
        $.ajax({
            async: false,
            type: "POST",
            url: prefixUrl+articleId,
            dataType: "json",
            success: function(data){
                $(element).html(data);
                $.cookie(key ,articleId,{"path":"/"});
                if(prj==0){
                    alert("点赞成功");
                    window.setTimeout("window.location.reload()", 1000);
                }
                return false;
            },
            error: function () {
                alert("数据出错！");
                return false;
            }
        });
        return false;
    }
}

$(function(){
    $(".sonCategory").hide();
    $(".parCategory").mouseover(function () {
        var categoryId = $(this).attr("id");
        $("#sonCategory-"+categoryId).show();
        return false;
    });
    $(".parCategory").mouseout(function () {
        var categoryId = $(this).attr("id");
        $("#sonCategory-"+categoryId).hide("slow");
        return false;
    })
})

function confirmDelete() {
    var msg = "您确定要删除吗？";
    if (confirm(msg) == true) {
        return true;
    } else {
        return false;
    }
}

/*删除评论*/
function deleteComment(commentId){
    if(confirmDelete()==true) {
        $.ajax({
            type:"DELETE",
            async:false,
            url:"/admin/comment/" + commentId,
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            dataType: "json",
            success:function (data) {
                alert(data.extend.msg);
                if(data.code==0){
                    return false;
                }else{
                    window.location.reload();
                    return false;
                }
            }
        });
    }
}

/*$("#search-form").submit(function () {
    $.ajax({
        async:false,
        type:"POST",
        url:"/search",
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        data: $("#search-form").serialize(),
    })
})*/
