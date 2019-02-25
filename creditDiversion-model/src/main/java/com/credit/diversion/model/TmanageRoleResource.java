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
 * @TmanageRoleResource
 * @角色权限(t_manage_role_resource)
 * @version : Ver 1.0
 */
@TableName("t_manage_role_resource")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmanageRoleResource implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:
	 * @字段:role_id BIGINT(19)
	 */
	private Long roleId;


	/**
	 * @备注:
	 * @字段:resource_id BIGINT(19)
	 */
	private Long resourceId;

}

