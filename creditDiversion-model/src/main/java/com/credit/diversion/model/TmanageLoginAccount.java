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
 * @TmanageLoginAccount
 * @后台登录帐号(t_manage_login_account)
 * @version : Ver 1.0
 */
@TableName("t_manage_login_account")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmanageLoginAccount implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:登陆账号
	 * @字段:login_name VARCHAR(30)
	 */
	private String loginName;


	/**
	 * @备注:登录密码
	 * @字段:login_password VARCHAR(100)
	 */
	private String loginPassword;


	/**
	 * @备注:角色ID
	 * @字段:user_role_id BIGINT(19)
	 */
	private Long userRoleId;


	/**
	 * @备注:登陆手机号
	 * @字段:user_phone VARCHAR(11)
	 */
	private String userPhone;


	/**
	 * @备注:人员姓名
	 * @字段:user_name VARCHAR(80)
	 */
	private String userName;


	/**
	 * @备注:QQ
	 * @字段:user_qq VARCHAR(30)
	 */
	private String userQq;


	/**
	 * @备注:工号
	 * @字段:user_work_number VARCHAR(30)
	 */
	private String userWorkNumber;


	/**
	 * @备注:所属公司
	 * @字段:company_id BIGINT(19)
	 */
	private Long companyId;


	/**
	 * @备注:所属部门
	 * @字段:department_id BIGINT(19)
	 */
	private Long departmentId;


	/**
	 * @备注:最后登录时间
	 * @字段:last_login_time DATETIME(19)
	 */
	private java.util.Date lastLoginTime;


	/**
	 * @备注:最后登录IP
	 * @字段:last_login_ip VARCHAR(30)
	 */
	private String lastLoginIp;


	/**
	 * @备注:状态0-正常；1-已停用
	 * @字段:account_status INT(10)
	 */
	private Integer accountStatus;


	/**
	 * @备注:类型0-公司员工；
	 * @字段:account_type INT(10)
	 */
	private Integer accountType;

	@TableField(exist = false)
	private String token;

}

