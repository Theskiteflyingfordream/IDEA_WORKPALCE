package com.ljw.blog.mapper;

import com.ljw.blog.entity.Notice;
import java.util.List;

public interface NoticeMapper {
    int deleteByPrimaryKey(Integer noticeId);

    int insert(Notice record);

    Notice selectByPrimaryKey(Integer noticeId);

    List<Notice> selectAll();

    int updateByPrimaryKey(Notice record);
}