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
 * @ToptLog
 * @(t_opt_log)
 * @version : Ver 1.0
 */
@TableName("t_opt_log")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ToptLog implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:
	 * @字段:log_id VARCHAR(32)
	 */
	@TableId(type = IdType.AUTO, value = "log_id")
	private String logId;


	/**
	 * @备注:
	 * @字段:opt_name VARCHAR(20)
	 */
	private String optName;


	/**
	 * @备注:
	 * @字段:opt_id VARCHAR(32)
	 */
	private String optId;


	/**
	 * @备注:
	 * @字段:opt_desc VARCHAR(50)
	 */
	private String optDesc;


	/**
	 * @备注:
	 * @字段:opt_time TIMESTAMP(19)
	 */
	private java.util.Date optTime;

}

