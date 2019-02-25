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
 * @Tmessage
 * @消息(t_message)
 * @version : Ver 1.0
 */
@TableName("t_message")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tmessage implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:
	 * @字段:id INT(10)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Integer id;


	/**
	 * @备注:0表示所有用户
	 * @字段:user_id VARCHAR(64)
	 */
	private String userId;


	/**
	 * @备注:标题
	 * @字段:title VARCHAR(200)
	 */
	private String title;


	/**
	 * @备注:内容
	 * @字段:content VARCHAR(3000)
	 */
	private String content;


	/**
	 * @备注:创建时间
	 * @字段:create_time DATETIME(19)
	 */
	private java.util.Date createTime;


	/**
	 * @备注:1：通知；2：公告；3：活动
	 * @字段:type INT(10)
	 */
	private Integer type;


	/**
	 * @备注:status1:已发布0：草稿
	 * @字段:status INT(10)
	 */
	private Integer status;


	/**
	 * @备注:生效时间
	 * @字段:start_time DATETIME(19)
	 */
	private java.util.Date startTime;


	/**
	 * @备注:结束时间
	 * @字段:finish_time DATETIME(19)
	 */
	private java.util.Date finishTime;

	/**
	 * 接收人登录号
	 */
	@TableField(exist = false)
	private String userLoginName;
}

