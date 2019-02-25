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
 * @TmanageLog
 * @后台日志(t_manage_log)
 * @version : Ver 1.0
 */
@TableName("t_manage_log")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmanageLog implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:登录帐号主键
	 * @字段:log_account_id BIGINT(19)
	 */
	private Long logAccountId;


	/**
	 * @备注:登录人姓名
	 * @字段:log_user_name VARCHAR(32)
	 */
	private String logUserName;


	/**
	 * @备注:操作模块
	 * @字段:log_resource VARCHAR(32)
	 */
	private String logResource;


	/**
	 * @备注:模块主键
	 * @字段:log_resource_id BIGINT(19)
	 */
	private Long logResourceId;


	/**
	 * @备注:操作时间
	 * @字段:log_operate DATETIME(19)
	 */
	private java.util.Date logOperate;


	/**
	 * @备注:操作IP地址
	 * @字段:log_ip_address VARCHAR(32)
	 */
	private String logIpAddress;


	/**
	 * @备注:操作内容
	 * @字段:log_content LONGTEXT(2147483647)
	 */
	private String logContent;

}

