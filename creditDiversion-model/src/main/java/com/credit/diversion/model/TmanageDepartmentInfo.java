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
 * @TmanageDepartmentInfo
 * @企业部门信息(t_manage_department_info)
 * @version : Ver 1.0
 */
@TableName("t_manage_department_info")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmanageDepartmentInfo implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:公司主键
	 * @字段:company_id BIGINT(19)
	 */
	private Long companyId;


	/**
	 * @备注:部门名称
	 * @字段:department_name VARCHAR(32)
	 */
	private String departmentName;


	/**
	 * @备注:状态0-正常；1-已停用
	 * @字段:department_status INT(10)
	 */
	private Integer departmentStatus;


	/**
	 * @备注:部门类型0-内部部门；
	 * @字段:department_type INT(10)
	 */
	private Integer departmentType;


	/**
	 * @备注:备注
	 * @字段:remark VARCHAR(20)
	 */
	private String remark;

}

