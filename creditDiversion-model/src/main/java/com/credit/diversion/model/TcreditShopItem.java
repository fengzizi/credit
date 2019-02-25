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
 * @TcreditShopItem
 * @贷超甲方产品信息(t_credit_shop_item)
 * @version : Ver 1.0
 */
@TableName("t_credit_shop_item")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TcreditShopItem implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键ID
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:信贷类目ID
	 * @字段:category_id BIGINT(19)
	 */
	private Long categoryId;


	/**
	 * @备注:app信贷名称
	 * @字段:app_name VARCHAR(100)
	 */
	private String appName;


	/**
	 * @备注:app小图标URL地址
	 * @字段:app_icon_url VARCHAR(512)
	 */
	private String appIconUrl;


	/**
	 * @备注:副标题
	 * @字段:sub_title VARCHAR(50)
	 */
	private String subTitle;


	/**
	 * @备注:app信贷id编号
	 * @字段:app_id VARCHAR(32)
	 */
	private String appId;


	/**
	 * @备注:0 下架 ；1 开启
	 * @字段:state INT(10)
	 */
	private Integer state;


	/**
	 * @备注:值为 荐，热，新
	 * @字段:tag VARCHAR(2)
	 */
	private String tag;


	/**
	 * @备注:最小额度值
	 * @字段:credit_min_limit INT(10)
	 */
	private Integer creditMinLimit;


	/**
	 * @备注:最大额度值
	 * @字段:credit_max_limit INT(10)
	 */
	private Integer creditMaxLimit;


	/**
	 * @备注:最大借款周期
	 * @字段:credit_max_term INT(10)
	 */
	private Integer creditMaxTerm;


	/**
	 * @备注:最小借款周期
	 * @字段:credit_min_term INT(10)
	 */
	private Integer creditMinTerm;


	/**
	 * @备注:最快放款时间
	 * @字段:sucess_pay_time VARCHAR(10)
	 */
	private String sucessPayTime;


	/**
	 * @备注:成功率
	 * @字段:sucess_rate DECIMAL(5)
	 */
	private java.math.BigDecimal sucessRate;


	/**
	 * @备注:日利息
	 * @字段:day_interest DECIMAL(7)
	 */
	private java.math.BigDecimal dayInterest;


	/**
	 * @备注:底部简短描述信息
	 * @字段:short_desc VARCHAR(80)
	 */
	private String shortDesc;


	/**
	 * @备注:跳转的商户H5地址
	 * @字段:app_tenant_url VARCHAR(512)
	 */
	private String appTenantUrl;


	/**
	 * @备注:创建时间
	 * @字段:created TIMESTAMP(19)
	 */
	private java.util.Date created;


	/**
	 * @备注:更新时间
	 * @字段:updated TIMESTAMP(19)
	 */
	private java.util.Date updated;

	@TableField(exist = false)
	private TitemComment titemComment;

	@TableField(exist = false)
	private TitemAttach titemAttach;

	@TableField(exist = false)
	private Integer creditCount;

	@TableField(exist = false)
	private Integer creditCountRe;

}

