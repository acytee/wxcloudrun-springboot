package com.tencent.wxcloudrun.model;

import com.tencent.wxcloudrun.dto.common.IBody;
import com.tencent.wxcloudrun.dto.common.EnumValid;
import com.tencent.wxcloudrun.utils.StrUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author : chenzg
 * @date : 2023-04-23 17:20:22
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "pt_user")
public class User implements IBody {
	@Id
	@Column(name = "`userId`", nullable = false)
	@ApiModelProperty(notes = "用户ID", hidden = true, example = "551547f9c74c4381a210487c66f50c0c")
	@Builder.Default
	private String userId = StrUtil.uuid();
	@Column(name = "`unionId`", nullable = false)
	@ApiModelProperty(notes = "微信unionId", required = true, example = "454957506e17440fb08de99d29c0f755")
	private String unionId;
	@Column(name = "`openId`", nullable = false)
	@ApiModelProperty(notes = "微信openId", required = true, example = "9c0f1f3e38a4451eb6283161fc434849")
	private String openId;
	@Column(name = "`nickName`")
	@ApiModelProperty(notes = "昵称", example = "8a81b4f41fb54e099786d0e86859eae6")
	private String nickName;
	@Column(name = "`avatarUrl`", nullable = false)
	@ApiModelProperty(notes = "头像", required = true, example = "d24f70f021fa4c95b7901d4709842aeb")
	private String avatarUrl;
	@Column(name = "`mobile`")
	@ApiModelProperty(notes = "手机号", example = "9c2449e8636b40d0b2e0e3166cb1d5f3")
	private String mobile;
	@Column(name = "`createTime`", nullable = false)
	@ApiModelProperty(notes = "创建时间", hidden = true, example = "2023-04-23 05:20:22")
	@Builder.Default
	private Date createTime = new Date();
	@Override
	public void setCreateUserId(String createUserId) {
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
			return false;
		}
		User that = (User) o;
		return userId != null && java.util.Objects.equals(userId, that.userId);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}}