create table SPRING_AI_CHAT_MEMORY
(
    conversation_id varchar(36) not null,
    content         text        not null,
    type            varchar(10) not null,
    timestamp       datetime    not null,
    constraint spring_ai_chat_memory_chk_1
        check (`type` in (_utf8mb4\'USER\',_utf8mb4\'ASSISTANT\',_utf8mb4\'SYSTEM\',_utf8mb4\'TOOL\'))
);

create index SPRING_AI_CHAT_MEMORY_CONVERSATION_ID_TIMESTAMP_IDX
    on SPRING_AI_CHAT_MEMORY (conversation_id, timestamp);

create table tb_ai_session
(
    id         bigint                             not null comment '主键id'
        primary key,
    user_id    bigint                             not null comment '用户id',
    summary    varchar(256)                       not null comment '会话摘要',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '会话记录表';

create table tb_ai_session_conversation
(
    id              bigint not null comment '主键'
        primary key,
    conversation_id bigint not null comment '对话id',
    session_id      bigint not null comment '会话id'
)
    comment '会话对话关联表';

create table tb_article
(
    id            bigint                               not null comment '文章主键id'
        primary key,
    title         varchar(256)                         not null comment '标题',
    summary       varchar(256)                         not null comment '文章摘要(50字以内)',
    cover_image   varchar(256)                         null comment '文章封面',
    content       text                                 not null comment '文章内容',
    is_draft      tinyint(1)                           null comment '是否是草稿',
    author_id     bigint                               not null comment '文章作者id',
    category_id   bigint                               null comment '文章所属分类',
    read_count    int        default 0                 not null comment '文章阅读数',
    like_count    int        default 0                 not null comment '点赞数',
    comment_count int        default 0                 not null comment '评论数量',
    created_at    datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at    datetime   default CURRENT_TIMESTAMP not null comment '更新时间',
    is_deleted    tinyint(1) default 0                 not null comment '逻辑删除(0:未删除;1:已删除)'
)
    comment '文章表';

create table tb_article_tag
(
    id         bigint auto_increment comment '主键'
        primary key,
    article_id bigint                             not null comment '文章    表主键映射',
    tag_id     bigint                             not null comment '标签表主键映射',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '文章_标签关联表';

create table tb_category
(
    id         bigint auto_increment comment '主键id'
        primary key,
    name       varchar(64)                        not null comment '分类名称',
    sort       int      default 0                 not null comment '分类排序值, 越小越靠前',
    count      int      default 0                 not null comment '该分类下的文章数量',
    status     tinyint  default 1                 not null comment '状态: 0-禁用, 1-启用',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint tb_category_pk
        unique (name)
)
    comment '帖子分类表';

create table tb_chat_conversation
(
    id              bigint                             not null comment '主键id'
        primary key,
    last_message_id bigint                             null comment '最后一条消息id',
    created_at      datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at      datetime default CURRENT_TIMESTAMP not null comment '更新时间'
)
    comment '聊天对话表';

create table tb_chat_conversation_user
(
    id              bigint                             not null comment '主键id'
        primary key,
    conversation_id bigint                             not null comment '外键关联对话表id',
    user_id         bigint                             not null comment '外键关联用户id',
    last_leave_time datetime default (now())           null comment '用户最后一次离开对话的时间',
    created_at      datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '对话用户关联表';

create table tb_chat_message
(
    id              bigint                             not null comment '主键ID'
        primary key,
    conversation_id bigint                             not null comment '关联对话外键id',
    sender_id       bigint                             not null comment '发送者ID',
    type            varchar(20)                        not null comment '消息类型',
    content         varchar(512)                       not null comment '消息内容',
    created_at      datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '聊天消息表';

create table tb_collection
(
    id            bigint                               not null comment '收藏集ID'
        primary key,
    name          varchar(100)                         not null comment '收藏集名称',
    description   varchar(512)                         null comment '描述',
    cover_image   varchar(256)                         null comment '封面图片URL',
    user_id       bigint                               not null comment '收藏集所属用户ID',
    article_count bigint     default 0                 not null comment '文章数量',
    is_public     tinyint(1) default 1                 not null comment '是否公开',
    created_at    datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at    datetime   default CURRENT_TIMESTAMP not null comment '更新时间',
    is_deleted    tinyint(1) default 0                 not null comment '是否删除(0:未删除,1:已删除)',
    constraint idx_user
        unique (user_id)
);

create table tb_collection_article
(
    id            bigint auto_increment comment 'ID'
        primary key,
    collection_id bigint                             not null comment '收藏集ID',
    article_id    bigint                             not null comment '文章ID',
    created_at    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint uk_collection_article
        unique (collection_id, article_id)
);

create table tb_comment
(
    id           bigint                             not null comment '评论主键ID'
        primary key,
    content      tinytext                           not null comment '评论内容',
    subject_id   bigint                             not null comment '被评论对象id(文章id...)',
    subject_type varchar(16)                        not null comment '被评论对象类型(文章...)',
    user_id      bigint                             not null comment '评论用户ID',
    created_at   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
);

create table tb_comment_reply
(
    id         bigint                             not null comment '回复主键ID'
        primary key,
    content    tinytext                           not null comment '回复内容',
    comment_id bigint                             not null comment '评论ID(指向tb_comment.id)',
    user_id    bigint                             not null comment '回复用户ID',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    at_user_id bigint                             null comment '被@的用户id(回复评论的回复视为@)',
    constraint tb_comment_reply_ibfk_1
        foreign key (comment_id) references tb_comment (id)
            on delete cascade
)
    comment '二级评论(回复)表';

create index idx_parent
    on tb_comment_reply (comment_id);

create index idx_user
    on tb_comment_reply (user_id);

create table tb_like
(
    id          bigint                             not null comment '主键id'
        primary key,
    target_id   bigint                             not null comment '点赞对象ID',
    user_id     bigint                             not null comment '用户ID',
    target_type varchar(20)                        not null comment '点赞对象类型',
    created_at  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint uk_target_user_type
        unique (user_id, target_id, target_type)
);

create table tb_notification
(
    id           bigint                               not null comment '主键ID'
        primary key,
    user_id      bigint                               not null comment '接收者ID',
    type         varchar(20)                          not null comment '通知类型',
    content      varchar(256)                         not null comment '通知内容',
    is_read      tinyint(1) default 0                 not null comment '通知是否已读',
    sender_id    bigint                               not null comment '发送者id',
    target_id    bigint                               null comment '相关目标ID(文章ID,评论ID)',
    target_title varchar(256)                         null comment '目标标题(文章标题,评论内容)',
    created_at   datetime   default CURRENT_TIMESTAMP not null comment '消息创建时间'
)
    comment '用户通知表';

create table tb_storage
(
    id         bigint                             not null comment '主键id'
        primary key,
    unique_id  varchar(256)                       not null comment '唯一标识',
    url        varchar(256)                       not null comment '文件url',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updated_at datetime default CURRENT_TIMESTAMP not null comment '更新时间',
    constraint ukey
        unique (unique_id)
)
    comment '文件表(唯一标识->url)';

create table tb_tag
(
    id         bigint auto_increment comment '标签ID'
        primary key,
    name       varchar(30)                        not null comment '标签名称',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    constraint uk_name
        unique (name)
)
    comment '标签表';

create table tb_user
(
    id          bigint                                                                                                    not null comment '主键ID'
        primary key,
    nickname    varchar(256)                                                                                              not null comment '用户昵称',
    username    varchar(64)                                                                                               not null comment '用户账号名',
    bio         tinytext                                                                                                  null comment '个人简介',
    avatar      varchar(256) default 'https://aiblog-1305314451.cos.ap-shanghai.myqcloud.com/avatar%2Fdefault_avatar.svg' not null comment '用户头像',
    password    varchar(64)                                                                                               not null comment '用户密码',
    phone       varchar(32)                                                                                               null comment '用户手机号',
    email       varchar(256)                                                                                              null comment '用户邮箱',
    total_likes int          default 0                                                                                    not null comment '总获赞数量',
    created_at  datetime                                                                                                  not null comment '创建时间',
    updated_at  datetime                                                                                                  not null comment '更新时间',
    is_deleted  tinyint(1)                                                                                                not null comment '逻辑删除,0:未删除;1:删除',
    role        int          default 1                                                                                    not null comment '用户权限(0:游客,1:普通用户,2:管理员)',
    constraint tb_user_pk
        unique (username)
)
    comment '用户管理表';

create table tb_user_follow
(
    id           bigint auto_increment comment '主键ID'
        primary key,
    follower_id  bigint                             not null comment '关注者ID',
    following_id bigint                             not null comment '被关注者ID',
    created_at   datetime default CURRENT_TIMESTAMP not null comment '关注时间',
    constraint uk_follower_following
        unique (follower_id, following_id),
    constraint fk_follower
        foreign key (follower_id) references tb_user (id),
    constraint fk_following
        foreign key (following_id) references tb_user (id)
)
    comment '用户关注关系表' collate = utf8mb4_unicode_ci;

create index idx_follower_id
    on tb_user_follow (follower_id);

create index idx_following_id
    on tb_user_follow (following_id);

create table tb_user_tag
(
    id         bigint auto_increment comment '主键'
        primary key,
    user_id    bigint                             not null comment '用户表主键映射',
    tag_id     bigint                             not null comment '标签表主键映射',
    created_at datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户_标签关联表';

