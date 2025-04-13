package com.zeuslu.blog.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zeuslu.blog.common.constant.TableFieldConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 * 数据库通用字段填充, 需要在MybatisPlus配置类中引入
 */

@Slf4j
public class MetaDataFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject, TableFieldConstant.CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, TableFieldConstant.UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, TableFieldConstant.DELETED_FLAG, () -> 0, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject, TableFieldConstant.UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
    }
}
