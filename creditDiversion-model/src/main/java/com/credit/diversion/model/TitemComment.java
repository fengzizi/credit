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
 * @TitemComment
 * @信贷评论统计(t_item_comment)
 * @version : Ver 1.0
 */
@TableName("t_item_comment")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TitemComment implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:
	 * @字段:item_id BIGINT(19)
	 */
	@TableId(type = IdType.INPUT, value = "item_id")
	private Long itemId;


	/**
	 * @备注:平均放款速度
	 * @字段:avg_pay_rate DOUBLE(22)
	 */
	private Double avgPayRate;


	/**
	 * @备注:平均难易程度
	 * @字段:avg_difficulty DOUBLE(22)
	 */
	private Double avgDifficulty;


	/**
	 * @备注:平均催收
	 * @字段:avg_collection DOUBLE(22)
	 */
	private Double avgCollection;

}

