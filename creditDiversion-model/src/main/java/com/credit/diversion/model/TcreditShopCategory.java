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
 * @TcreditShopCategory
 * @信贷类型分类(t_credit_shop_category)
 * @version : Ver 1.0
 */
@TableName("t_credit_shop_category")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TcreditShopCategory implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:类目ID
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:父类目ID=0时，代表的是一级的类目
	 * @字段:parent_id BIGINT(19)
	 */
	private Long parentId;


	/**
	 * @备注:分类名称
	 * @字段:name VARCHAR(50)
	 */
	private String name;


	/**
	 * @备注:状态。可选值:1(正常),2(删除)
	 * @字段:status INT(10)
	 */
	private Integer status;


	/**
	 * @备注:排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
	 * @字段:sort_order INT(10)
	 */
	private Integer sortOrder;


	/**
	 * @备注:该类目是否为父类目，1为true，0为false
	 * @字段:parent BIT(0)
	 */
	private Boolean parent;


	/**
	 * @备注:创建时间
	 * @字段:created TIMESTAMP(19)
	 */
	private java.util.Date created;


	/**
	 * @备注:创建时间
	 * @字段:updated TIMESTAMP(19)
	 */
	private java.util.Date updated;

}

