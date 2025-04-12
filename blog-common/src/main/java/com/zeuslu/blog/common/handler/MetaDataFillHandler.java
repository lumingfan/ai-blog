package com.zeuslu.blog.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.zeuslu.blog.common.constant.TableColumnConstant;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * @author lumingfan
 * 数据库通用字段填充, 需要在MybatisPlus配置类中引入
 */
public class MetaDataFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject, TableColumnConstant.CREATE_TIME, LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, TableColumnConstant.UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject, TableColumnConstant.DELETED_FLAG, () -> 0, Integer.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject, TableColumnConstant.UPDATE_TIME, LocalDateTime::now, LocalDateTime.class);
    }
}
