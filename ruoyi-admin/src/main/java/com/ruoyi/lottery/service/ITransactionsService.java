package com.ruoyi.lottery.service;

import java.util.List;

import com.ruoyi.lottery.domain.Prize;
import com.ruoyi.lottery.domain.Transactions;

/**
 * 信用卡交易记录Service接口
 *
 * @author ruoyi
 * @date 2025-07-24
 */
public interface ITransactionsService
{
    /**
     * 查询信用卡交易记录
     *
     * @param acctnbr 信用卡交易记录主键
     * @return 信用卡交易记录
     */
    public Transactions selectTransactionsByAcctnbr(Long acctnbr);

    /**
     * 查询信用卡交易记录列表
     *
     * @param transactions 信用卡交易记录
     * @return 信用卡交易记录集合
     */
    public List<Transactions> selectTransactionsList(Transactions transactions);

    /**
     * 新增信用卡交易记录
     *
     * @param transactions 信用卡交易记录
     * @return 结果
     */
    public int insertTransactions(Transactions transactions);

    /**
     * 修改信用卡交易记录
     *
     * @param transactions 信用卡交易记录
     * @return 结果
     */
    public int updateTransactions(Transactions transactions);

    /**
     * 批量删除信用卡交易记录
     *
     * @param acctnbrs 需要删除的信用卡交易记录主键集合
     * @return 结果
     */
    public int deleteTransactionsByAcctnbrs(Long[] acctnbrs);

    /**
     * 删除信用卡交易记录信息
     *
     * @param acctnbr 信用卡交易记录主键
     * @return 结果
     */
    public int deleteTransactionsByAcctnbr(Long acctnbr);

    /**
     * 抽奖接口
     *
     * @param prizes 奖品信息
     * @return 结果
     */
    public List<Transactions> lottery(List<Prize> prizes);
}
