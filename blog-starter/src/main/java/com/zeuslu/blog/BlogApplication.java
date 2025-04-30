package com.zeuslu.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author lumingfan
 */
@SpringBootApplication
@MapperScan({
        "com.zeuslu.blog.user.mapper",
        "com.zeuslu.blog.article.mapper",
        "com.zeuslu.blog.tag.mapper",
        "com.zeuslu.blog.storage.mapper",
        "com.zeuslu.blog.like.mapper",
})
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
