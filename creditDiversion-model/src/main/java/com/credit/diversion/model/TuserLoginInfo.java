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
 * @TuserLoginInfo
 * @用户登录信息(t_user_login_info)
 * @version : Ver 1.0
 */
@TableName("t_user_login_info")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TuserLoginInfo implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:作为用户ID
	 * @字段:id VARCHAR(32)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private String id;


	/**
	 * @备注:登录号
	 * @字段:login_name VARCHAR(32)
	 */
	private String loginName;


	/**
	 * @备注:登录密码
	 * @字段:login_pass VARCHAR(64)
	 */
	private String loginPass;


	/**
	 * @备注:盐值
	 * @字段:login_salt VARCHAR(64)
	 */
	private String loginSalt;


	/**
	 * @备注:登录状态
	 * @字段:login_status VARCHAR(8)
	 */
	private String loginStatus;


	/**
	 * @备注:最后登录时间
	 * @字段:last_login_time TIMESTAMP(19)
	 */
	private java.util.Date lastLoginTime;


	/**
	 * @备注:登录方式 1:短信登录；密码登录
	 * @字段:login_type BIT(0)
	 */
	private Boolean loginType;


	/**
	 * @备注:修改前登录用户号
	 * @字段:loginid_beformodify VARCHAR(32)
	 */
	private String loginidBeformodify;


	/**
	 * @备注:创建时间
	 * @字段:created TIMESTAMP(19)
	 */
	private java.util.Date created;


	/**
	 * @备注:最后修改时间 
	 * @字段:updated TIMESTAMP(19)
	 */
	private java.util.Date updated;

}

