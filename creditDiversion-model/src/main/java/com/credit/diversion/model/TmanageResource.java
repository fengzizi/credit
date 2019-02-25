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
 * @TmanageResource
 * @模块资源(t_manage_resource)
 * @version : Ver 1.0
 */
@TableName("t_manage_resource")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmanageResource implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:父主键
	 * @字段:pid BIGINT(19)
	 */
	private Long pid;


	/**
	 * @备注:模块名称
	 * @字段:resource_name VARCHAR(32)
	 */
	private String resourceName;


	/**
	 * @备注:链接地址
	 * @字段:resource_url VARCHAR(64)
	 */
	private String resourceUrl;


	/**
	 * @备注:类型0-模块;1-菜单
	 * @字段:resource_type INT(10)
	 */
	private Integer resourceType;


	/**
	 * @备注:菜单图标
	 * @字段:resource_icon VARCHAR(8)
	 */
	private String resourceIcon;


	/**
	 * @备注:排列顺序
	 * @字段:resource_order INT(10)
	 */
	private Integer resourceOrder;


	/**
	 * @备注:状态0-正常；1-已停用 
	 * @字段:resource_status INT(10)
	 */
	private Integer resourceStatus;


	/**
	 * @备注:分组信息0-普通用户；1-管理员
	 * @字段:resource_group INT(10)
	 */
	private Integer resourceGroup;

}

