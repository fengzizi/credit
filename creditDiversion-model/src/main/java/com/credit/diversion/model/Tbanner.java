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
 * @Tbanner
 * @首页banner记录(t_banner)
 * @version : Ver 1.0
 */
@TableName("t_banner")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tbanner implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:子标题
	 * @字段:sub_title VARCHAR(100)
	 */
	private String subTitle;


	/**
	 * @备注:标题描述
	 * @字段:title_desc VARCHAR(500)
	 */
	private String titleDesc;


	/**
	 * @备注:跳转链接
	 * @字段:point_url VARCHAR(1024)
	 */
	private String pointUrl;


	/**
	 * @备注:图片绝对路径
	 * @字段:pic_url VARCHAR(1024)
	 */
	private String picUrl;


	/**
	 * @备注:
	 * @字段:created TIMESTAMP(19)
	 */
	private java.util.Date created;


	/**
	 * @备注:
	 * @字段:updated TIMESTAMP(19)
	 */
	private java.util.Date updated;

}

