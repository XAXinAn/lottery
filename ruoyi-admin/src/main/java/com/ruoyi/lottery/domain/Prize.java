package com.ruoyi.lottery.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
/**
 * 交易记录对象 transactions
 *
 * @author ruoyi
 * @date 2025-07-23
 */
public class Prize extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 最大金额 */
    @Excel(name = "最大金额")
    private double maxAmount;

    /** 最大金额 */
    @Excel(name = "最小金额")
    private double minAmount;

    /** 名额 */
    @Excel(name = "名额")
    private int num;

    /** 预算 */
    @Excel(name = "预算")
    private double budget;

    public Prize(double minAmount, double maxAmount, int num, double budget) {
        this.maxAmount = maxAmount;
        this.minAmount = minAmount;
        this.num = num;
        this.budget = budget;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(double minAmount) {
        this.minAmount = minAmount;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public String toString() {
        return "Prize{" +
                "maxAmount=" + maxAmount +
                ", minAmount=" + minAmount +
                ", num=" + num +
                ", budget=" + budget +
                '}';
    }
}
