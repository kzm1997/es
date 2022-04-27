package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class PostMainInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 帖子编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 发帖人账号
     */
    private Integer accId;

    /**
     * 帖子标签
     */
    private String tag;

    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 帖子状态 1正常 11删除 12小黑屋
     */
    private String status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 最后回帖时间
     */
    private LocalDateTime lastCommentTime;


}
