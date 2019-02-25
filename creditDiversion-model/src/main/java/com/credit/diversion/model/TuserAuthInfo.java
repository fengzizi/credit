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
 * @TuserAuthInfo
 * @用户认证信息扩展(t_user_auth_info)
 * @version : Ver 1.0
 */
@TableName("t_user_auth_info")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TuserAuthInfo implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:
	 * @字段:uid VARCHAR(32)
	 */
	@TableId(type = IdType.AUTO, value = "uid")
	private String uid;


	/**
	 * @备注:用户真实姓名
	 * @字段:real_name VARCHAR(128)
	 */
	private String realName;


	/**
	 * @备注:身份证号
	 * @字段:id_card VARCHAR(32)
	 */
	private String idCard;


	/**
	 * @备注:手机号码
	 * @字段:user_phone VARCHAR(32)
	 */
	private String userPhone;


	/**
	 * @备注:
	 * @字段:created_by VARCHAR(32)
	 */
	private String createdBy;


	/**
	 * @备注:
	 * @字段:created_at DATETIME(19)
	 */
	private java.util.Date createdAt;


	/**
	 * @备注:
	 * @字段:updated_by VARCHAR(32)
	 */
	private String updatedBy;


	/**
	 * @备注:
	 * @字段:updated_at DATETIME(19)
	 */
	private java.util.Date updatedAt;

}

