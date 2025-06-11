package com.zeuslu.blog;

import com.zeuslu.blog.api.tag.service.TagService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlogApplicationTest {
    @Autowired
    private TagService tagService;

    @Test
    public void test() {
        tagService.saveTag("test");
    }

}