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
 * @TitemAttach
 * @信贷附属(t_item_attach)
 * @version : Ver 1.0
 */
@TableName("t_item_attach")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TitemAttach implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:
	 * @字段:item_id BIGINT(19)
	 */
	@TableId(type = IdType.INPUT, value = "item_id")
	private Long itemId;


	/**
	 * @备注:申请条件
	 * @字段:apply_condition VARCHAR(500)
	 */
	private String applyCondition;


	/**
	 * @备注:所需材料
	 * @字段:request_material VARCHAR(500)
	 */
	private String requestMaterial;


	/**
	 * @备注:平台描述
	 * @字段:platform_desc VARCHAR(500)
	 */
	private String platformDesc;

}

