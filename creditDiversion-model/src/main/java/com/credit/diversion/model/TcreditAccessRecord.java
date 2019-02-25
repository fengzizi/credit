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
 * @TcreditAccessRecord
 * @(t_credit_access_record)
 * @version : Ver 1.0
 */
@TableName("t_credit_access_record")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TcreditAccessRecord implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键id
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:
	 * @字段:credit_id BIGINT(19)
	 */
	private Long creditId;


	/**
	 * @备注:信贷名称
	 * @字段:credit_name VARCHAR(255)
	 */
	private String creditName;


	/**
	 * @备注:用户id/访问者唯一识别码
	 * @字段:user_id VARCHAR(255)
	 */
	private String userId;


	/**
	 * @备注:用户名称
	 * @字段:user_name VARCHAR(255)
	 */
	private String userName;


	/**
	 * @备注:是否已注册：1=是；2=否
	 * @字段:is_register INT(10)
	 */
	private Integer isRegister;


	/**
	 * @备注:创建时间
	 * @字段:create_time DATETIME(19)
	 */
	private java.util.Date createTime;

}

