package com.ljw.blog.service.impl;

import com.ljw.blog.entity.Tag;
import com.ljw.blog.mapper.TagMapper;
import com.ljw.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TagServiceImpl
 * @Description TODO
 * @DATE 2021/10/26 20:32
 */

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getAllTags(){
        return tagMapper.selectAll();
    }

}
