package com.itheima.hchat.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 投放平台授权表
 * </p>
 *
 * @author mjc
 * @since 2021-08-31
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class LaunchPlatformAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 1-快手,2-头条
     */
    private Integer type;

    /**
     * 业务主键id-对应投放账户表
     */
    private Long businessId;

    /**
     * 访问令牌
     */
    private String accessToken;

    /**
     * 访问令牌过期时间
     */
    private Date accessTokenExpiresIn;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 刷新令牌过期时间
     */
    private Date refreshTokenExpiresIn;

    /**
     * 广告主id
     */
    private Long advertiserId;

    /**
     * 已授权账户所有的account_id
     * <p>
     * 早期设计问题,该字段与advertiserId始终保持一致
     */
    private String advertiserIds;

    /**
     * 扩展字段,生成授权url时自定义参数
     */
    private String state;

    /**
     * 当前token是否有效,0否1是
     */
    private Integer enable;

    /**
     * 真实业务主键id-对应投放账户表
     * 针对于头条的容错字段：授权广告主在我方后台投放账户、媒体对应不一致
     * 解决头条批量授权后，由于投放账户媒体对应不一致导致 刷新token失败问题
     */
    private Long refreshBusinessId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 投放平台的应用id
     */
    private String appId;

    /**
     * 投放平台的应用秘钥
     */
    private String secret;

    /**
     * 管家账户id
     */
    private String butlerAccountId;

    /**
     * 管家账户名称
     */
    private String butlerAccountName;

    /**
     * 用户账户id
     */
    private String userAccountId;

    /**
     * 用户账号名称
     */
    private String userAccountName;
}