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
 * @Tarticle
 * @文章信息(t_article)
 * @version : Ver 1.0
 */
@TableName("t_article")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tarticle implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:id主键
	 * @字段:id INT UNSIGNED(10)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Integer id;


	/**
	 * @备注:文章地址
	 * @字段:url VARCHAR(50)
	 */
	private String url;


	/**
	 * @备注:文章标题
	 * @字段:title VARCHAR(200)
	 */
	private String title;


	/**
	 * @备注:文章标题摘要信息
	 * @字段:title_desc VARCHAR(500)
	 */
	private String titleDesc;


	/**
	 * @备注:文章来源
	 * @字段:source VARCHAR(20)
	 */
	private String source;


	/**
	 * @备注:文章图片URL
	 * @字段:image VARCHAR(255)
	 */
	private String image;


	/**
	 * @备注:创建人
	 * @字段:created_by VARCHAR(32)
	 */
	private String createdBy;


	/**
	 * @备注:创建时间
	 * @字段:created_at DATETIME(19)
	 */
	private java.util.Date createdAt;


	/**
	 * @备注:更新人
	 * @字段:updated_by VARCHAR(32)
	 */
	private String updatedBy;


	/**
	 * @备注:更新时间
	 * @字段:updated_at DATETIME(19)
	 */
	private java.util.Date updatedAt;

}

