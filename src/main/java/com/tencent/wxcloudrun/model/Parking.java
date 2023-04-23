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
 * @date : 2023-04-23 17:20:21
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
@Table(name = "pt_parking")
public class Parking implements IBody {
    @Id
    @Column(name = "`parkingId`", nullable = false)
    @ApiModelProperty(notes = "车位ID", hidden = true, example = "08325bcd1e0b44abb93ee7c4d732675e")
    @Builder.Default
    private String parkingId = StrUtil.uuid();
    @Column(name = "`type`", nullable = false)
    @ApiModelProperty(notes = "车位类型（1可租，2想租）", required = true, example = "1")
    @EnumValid(prefix = "PARKING_TYPE_")
    private Integer type;
    @Column(name = "`status`", nullable = false)
    @ApiModelProperty(notes = "状态：1上架，0下架", required = true, example = "1")
    @EnumValid(prefix = "PARKING_STATUS_")
    private Integer status;
    @Column(name = "`color`", nullable = false)
    @ApiModelProperty(notes = "颜色：1绿色可租，2黄色有人租，3蓝色想租，4红色异常", required = true, example = "1")
    @EnumValid(prefix = "PARKING_COLOR_")
    private Integer color;
    @Column(name = "`browsers`", nullable = false)
    @ApiModelProperty(notes = "浏览人数", required = true, example = "1")
    @EnumValid(prefix = "PARKING_BROWSERS_")
    private Integer browsers;
    @Column(name = "`rent`", nullable = false)
    @ApiModelProperty(notes = "租金（元）", required = true, example = "1")
    @EnumValid(prefix = "PARKING_RENT_")
    private Integer rent;
    @Column(name = "`address`", nullable = false)
    @ApiModelProperty(notes = "地址", required = true, example = "c027796b922e47288978d65e76d8f3bd")
    private String address;
    @Column(name = "`longitude`", nullable = false)
    @ApiModelProperty(notes = "经度", required = true, example = "1")
    private Double longitude;
    @Column(name = "`latitude`", nullable = false)
    @ApiModelProperty(notes = "纬度", required = true, example = "1")
    private Double latitude;
    @Column(name = "`week`")
    @ApiModelProperty(notes = "星期（[1,2,3,4,5,6,7]）", example = "d9a71102e6e4437da91c89d097cbe27d")
    private String week;
    @Column(name = "`st`")
    @ApiModelProperty(notes = "开始时间（08:00）", example = "511ef6e60e4d4751b326bec13e7019ed")
    private String st;
    @Column(name = "`et`")
    @ApiModelProperty(notes = "结束时间（20:00）", example = "b8439660746d4df18ec5cd33c0fd6295")
    private String et;
    @Column(name = "`createUserId`", nullable = false)
    @ApiModelProperty(notes = "创建人", required = true, example = "15a7a7fa1d514b90a7ade527f025c315")
    private String createUserId;
    @Column(name = "`createTime`", nullable = false)
    @ApiModelProperty(notes = "创建时间", hidden = true, example = "2023-04-23 05:20:22")
    @Builder.Default
    private Date createTime = new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Parking that = (Parking) o;
        return parkingId != null && java.util.Objects.equals(parkingId, that.parkingId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}