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
public class PostOfficalInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 帖子编号
     */
    private Integer id;

    /**
     * 官方回复的账号
     */
    private Integer accId;

    /**
     * 回复状态 1未回复 2 已回复
     */
    private String commentStatus;

    /**
     * 回复时间
     */
    private LocalDateTime commentTime;


}
