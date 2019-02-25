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
 * @TmanageCompanyInfo
 * @公司列(t_manage_company_info)
 * @version : Ver 1.0
 */
@TableName("t_manage_company_info")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TmanageCompanyInfo implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:公司主键
	 * @字段:id BIGINT(19)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private Long id;


	/**
	 * @备注:公司名称
	 * @字段:company_name VARCHAR(64)
	 */
	private String companyName;


	/**
	 * @备注:公司地址
	 * @字段:company_address VARCHAR(256)
	 */
	private String companyAddress;


	/**
	 * @备注:类型0-总部；1-分公司
	 * @字段:company_type INT(10)
	 */
	private Integer companyType;


	/**
	 * @备注:状态0-正常；1-已停用
	 * @字段:company_status INT(10)
	 */
	private Integer companyStatus;


	/**
	 * @备注:备注
	 * @字段:remark VARCHAR(100)
	 */
	private String remark;


	/**
	 * @备注:联系电话
	 * @字段:company_phone VARCHAR(32)
	 */
	private String companyPhone;

}

