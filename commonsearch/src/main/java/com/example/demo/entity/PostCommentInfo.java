package com.example.demo.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2022-04-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PostCommentInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 回帖信息编号
     */
    private Integer id;

    /**
     * 帖子编号
     */
    private Integer mainId;

    /**
     * 回帖人账号
     */
    private Integer accId;

    /**
     * 是否官方回复
     */
    private Integer officalType;

    /**
     * 评论目标id,如果为帖子,则为帖子id
     */
    private Integer entityId;

    /**
     * 评论目标类型1帖子 2评论
     */
    private Integer entityType;

    /**
     * 评论目标的账号
     */
    private Integer targetAccId;

    /**
     * 回帖内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 编辑时间
     */
    private LocalDateTime updateTime;

    /**
     * 回帖的状态 1正常 11删除 12小黑屋
     */
    private Integer status;


}
