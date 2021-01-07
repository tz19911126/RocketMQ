package com.roy.rocketmq.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhangjk
 * @since 2020-04-14 15:18:11
 */
@ApiModel(description = "问诊产生的订单表", value = "Wz03Entity")
@Data
@Entity
@Table(name = "wz03")
public class Wz03Entity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("问诊单id")
    private Long wwz301;

    @ApiModelProperty("订单类型，0-问诊订单，1-服务包订单")
    private int wwz300;

    @ApiModelProperty("问诊服务id")
    private Long wwz101;

    @ApiModelProperty("问诊时段id")
    private Long wwz201;

    @ApiModelProperty("是否就诊0否1是,默认0")
    private String wwz302;

    @ApiModelProperty("就诊人id")
    private Long wwz901;

    @ApiModelProperty("就诊人姓名")
    private String wwz902;

    @ApiModelProperty("就诊人电话")
    private String wwz904;

    @ApiModelProperty("病情")
    private String wwz304;

    @ApiModelProperty("订单费用")
    private BigDecimal wwz305;

    @ApiModelProperty("支付方式：00全医保42微信52支付宝62云闪付")
    private String wwz306;

    @ApiModelProperty("状态默认0未支付，1已支付，2已取消")
    private String wwz307;

    @ApiModelProperty("支付状态自费（说明：0代表未支付1代表已经支付x代表未使用）第二位代表医保（说明：0代表未支付1代表已经支付x代表未使用)")
    private String wwz308;

    @ApiModelProperty("结算类型（1医保 0自费）")
    private String wwz309;

    @ApiModelProperty("支付状态(两位，第一位代表自费（说明：0代表未支付1代表已经支付x代表未使用）第二位代表医保（说明：0代表未支付1代表已经支付x代表未使用)")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date wwz310;

    @ApiModelProperty("平台生成的付款订单号")
    private String wwz311;

    @ApiModelProperty("第三方线上支付平台订单号")
    private String wwz312;

    @ApiModelProperty("平台生成的退款订单号")
    private String wwz313;
    @ApiModelProperty("总费用")
    private BigDecimal wwz314;

    @ApiModelProperty("医生可提现金额")
    private BigDecimal wwz315;

    @ApiModelProperty("医保统筹支付金额")
    private BigDecimal feeCoordination;

    @ApiModelProperty("医保个人账户支付金额")
    private BigDecimal feePersonalAccount;

    @ApiModelProperty("自费支付金额=费用-医保统筹-医保账户支付-医保其他支付金额")
    private BigDecimal feePersonalWallet;

    @ApiModelProperty("医保其他支付金额")
    private BigDecimal feeOther;

    @ApiModelProperty("社保卡号")
    private String cardNo;

    @ApiModelProperty("卡类型(3社保卡)")
    private String cardType;

    private String sid;

    @ApiModelProperty("医生编号(wz06.wwz601)")
    private Long wwz601;

    @ApiModelProperty("问诊医生姓名")
    private String wwz602;

    @ApiModelProperty("创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cco101;

    @ApiModelProperty("是否就该病情到医院就诊过")
    private String wwz318;

    @ApiModelProperty("附件id,多张以逗号分隔")
    private String wwz319;
}