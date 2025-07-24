package com.ruoyi.lottery.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 信用卡交易记录对象 transactions
 *
 * @author ruoyi
 * @date 2025-07-24
 */
public class Transactions extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 信用卡账号 */
    @Excel(name = "信用卡账号")
    private Long acctnbr;

    /** 交易日期 */
    @Excel(name = "交易日期")
    private Long inpDate;

    /** 交易时间 */
    @Excel(name = "交易时间")
    private Long inpTime;

    /** 交易序号 */
    @Excel(name = "交易序号")
    private Long xtranno;

    /** 交易金额 */
    @Excel(name = "交易金额")
    private BigDecimal billAmt;

    /** 支行号 */
    @Excel(name = "支行号")
    private String org1Code;

    /** 网点号 */
    @Excel(name = "网点号")
    private String orgNo;

    /** 客户内码(加密) */
    @Excel(name = "客户内码(加密)")
    private String custIsn;

    /** 卡片状态 */
    @Excel(name = "卡片状态")
    private String cardStatusTxt;

    public void setAcctnbr(Long acctnbr)
    {
        this.acctnbr = acctnbr;
    }

    public Long getAcctnbr()
    {
        return acctnbr;
    }

    public void setInpDate(Long inpDate)
    {
        this.inpDate = inpDate;
    }

    public Long getInpDate()
    {
        return inpDate;
    }

    public void setInpTime(Long inpTime)
    {
        this.inpTime = inpTime;
    }

    public Long getInpTime()
    {
        return inpTime;
    }

    public void setXtranno(Long xtranno)
    {
        this.xtranno = xtranno;
    }

    public Long getXtranno()
    {
        return xtranno;
    }

    public void setBillAmt(BigDecimal billAmt)
    {
        this.billAmt = billAmt;
    }

    public BigDecimal getBillAmt()
    {
        return billAmt;
    }

    public void setOrg1Code(String org1Code)
    {
        this.org1Code = org1Code;
    }

    public String getOrg1Code()
    {
        return org1Code;
    }

    public void setOrgNo(String orgNo)
    {
        this.orgNo = orgNo;
    }

    public String getOrgNo()
    {
        return orgNo;
    }

    public void setCustIsn(String custIsn)
    {
        this.custIsn = custIsn;
    }

    public String getCustIsn()
    {
        return custIsn;
    }

    public void setCardStatusTxt(String cardStatusTxt)
    {
        this.cardStatusTxt = cardStatusTxt;
    }

    public String getCardStatusTxt()
    {
        return cardStatusTxt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("acctnbr", getAcctnbr())
            .append("inpDate", getInpDate())
            .append("inpTime", getInpTime())
            .append("xtranno", getXtranno())
            .append("billAmt", getBillAmt())
            .append("org1Code", getOrg1Code())
            .append("orgNo", getOrgNo())
            .append("custIsn", getCustIsn())
            .append("cardStatusTxt", getCardStatusTxt())
            .toString();
    }

    public Transactions(Long acctnbr, Long inpDate, Long inpTime, Long xtranno, BigDecimal billAmt, String org1Code, String orgNo, String custIsn, String cardStatusTxt) {
        this.acctnbr = acctnbr;
        this.inpDate = inpDate;
        this.inpTime = inpTime;
        this.xtranno = xtranno;
        this.billAmt = billAmt;
        this.org1Code = org1Code;
        this.orgNo = orgNo;
        this.custIsn = custIsn;
        this.cardStatusTxt = cardStatusTxt;
    }
}
