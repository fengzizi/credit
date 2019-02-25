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
 * @Tbroadcast
 * @APP广播里的数据(t_broadcast)
 * @version : Ver 1.0
 */
@TableName("t_broadcast")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tbroadcast implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键ID号
	 * @字段:id BIGINT UNSIGNED(20)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:业务类型 1:APP首页广播内容
	 * @字段:biz_type VARCHAR(20)
	 */
	private String bizType;


	/**
	 * @备注:广播内容
	 * @字段:content VARCHAR(255)
	 */
	private String content;


	/**
	 * @备注:0:开启；1:关闭
	 * @字段:biz_status INT(10)
	 */
	private Integer bizStatus;


	/**
	 * @备注:创建时间
	 * @字段:created_at DATETIME(19)
	 */
	private java.util.Date createdAt;


	/**
	 * @备注:创建人
	 * @字段:created_by VARCHAR(64)
	 */
	private String createdBy;


	/**
	 * @备注:更新时间
	 * @字段:updated_at DATETIME(19)
	 */
	private java.util.Date updatedAt;


	/**
	 * @备注:更新人
	 * @字段:updated_by VARCHAR(64)
	 */
	private String updatedBy;

}

