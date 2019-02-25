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
 * @TmanageRoleInfo
 * @角色信息(t_manage_role_info)
 * @version : Ver 1.0
 */
@TableName("t_manage_role_info")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmanageRoleInfo implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:角色名称
	 * @字段:role_name VARCHAR(32)
	 */
	private String roleName;


	/**
	 * @备注:备注
	 * @字段:role_remark VARCHAR(64)
	 */
	private String roleRemark;


	/**
	 * @备注:状态0-正常；1-已停用
	 * @字段:role_status INT(10)
	 */
	private Integer roleStatus;


	/**
	 * @备注:类型0-普通用户；1-系统管理员
	 * @字段:role_type INT(10)
	 */
	private Integer roleType;

}

