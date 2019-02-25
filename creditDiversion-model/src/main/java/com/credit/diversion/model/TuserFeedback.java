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
 * @TuserFeedback
 * @用户反馈(t_user_feedback)
 * @version : Ver 1.0
 */
@TableName("t_user_feedback")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TuserFeedback implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/**
	 * @备注:主键
	 * @字段:id VARCHAR(100)
	 */
	@TableId(type = IdType.AUTO, value = "id")
	private String id;


	/**
	 * @备注:
	 * @字段:uid VARCHAR(32)
	 */
	private String uid;


	/**
	 * @备注:反馈内容
	 * @字段:feedback_content VARCHAR(2048)
	 */
	private String feedbackContent;


	/**
	 * @备注:反馈类型
	 * @字段:feedback_type VARCHAR(32)
	 */
	private String feedbackType;


	/**
	 * @备注:客户号
	 * @字段:created_by VARCHAR(32)
	 */
	private String createdBy;


	/**
	 * @备注:反馈提交时间
	 * @字段:created_at TIMESTAMP(19)
	 */
	private java.util.Date createdAt;


	/**
	 * @备注:处理人
	 * @字段:updated_by VARCHAR(32)
	 */
	private String updatedBy;


	/**
	 * @备注:处理时间
	 * @字段:updated_at DATETIME(19)
	 */
	private java.util.Date updatedAt;


	/**
	 * @备注:处理意见
	 * @字段:handled_suggestion VARCHAR(512)
	 */
	private String handledSuggestion;


	/**
	 * @备注:
	 * @字段:app_version VARCHAR(32)
	 */
	private String appVersion;


	/**
	 * @备注:
	 * @字段:sys_version VARCHAR(32)
	 */
	private String sysVersion;


	/**
	 * @备注:
	 * @字段:phone_brand VARCHAR(32)
	 */
	private String phoneBrand;

	/**
	 * 用户个人信息
	 */
	@TableField(exist = false)
	private TuserPersonInfo tuserPersonInfo;

	/**
	 * 用户认证信息
	 */
	@TableField(exist = false)
	private TuserAuthInfo tuserAuthInfo;

}

