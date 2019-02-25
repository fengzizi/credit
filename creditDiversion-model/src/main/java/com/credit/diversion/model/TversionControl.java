package com.credit.diversion.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @version : Ver 1.0
 * @TversionControl
 * @审核开关(t_version_control)
 */
@TableName("t_version_control")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TversionControl implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * @备注:主键ID
     * @字段:id BIGINT UNSIGNED(20)
     */
    @TableId(type = IdType.AUTO, value = "id")
    private Long id;


    /**
     * @备注:手机操作系统
     * @字段:os_type VARCHAR(10)
     */
    private String osType;


    /**
     * @备注:手机系统版本号
     * @字段:os_version VARCHAR(10)
     */
    private String osVersion;


    /**
     * @备注:手机构建的Build号
     * @字段:os_build_num VARCHAR(4)
     */
    private String osBuildNum;


    /**
     * @备注:APP审核开关 ON:开启审核; OFF:关闭审核
     * @字段:audit_switch VARCHAR(5)
     */
    private String auditSwitch;


    /**
     * @备注:创建时间
     * @字段:created_at TIMESTAMP(19)
     */
    private java.util.Date createdAt;


    /**
     * @备注:创建者
     * @字段:created_by VARCHAR(32)
     */
    private String createdBy;


    /**
     * @备注:更新时间
     * @字段:updated_at TIMESTAMP(19)
     */
    private java.util.Date updatedAt;


    /**
     * @备注:更新者
     * @字段:updated_by VARCHAR(32)
     */
    private String updatedBy;

}

