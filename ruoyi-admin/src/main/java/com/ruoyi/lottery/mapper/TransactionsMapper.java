package com.ruoyi.lottery.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.lottery.domain.Prize;
import com.ruoyi.lottery.domain.Transactions;
import com.ruoyi.lottery.domain.WinTransactions;

/**
 * 信用卡交易记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-07-24
 */
public interface TransactionsMapper
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
     * 删除信用卡交易记录
     *
     * @param acctnbr 信用卡交易记录主键
     * @return 结果
     */
    public int deleteTransactionsByAcctnbr(Long acctnbr);

    /**
     * 批量删除信用卡交易记录
     *
     * @param acctnbrs 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTransactionsByAcctnbrs(Long[] acctnbrs);

    /**
     * 查询所有交易记录
     *
     * @return 交易记录集合
     */
    public List<Transactions> selectAllTransactions();

    /**
     * 查询指定金额范围内的交易记录
     *
     * @param prize 奖项信息
     * @return 交易记录集合
     */
    public List<Transactions> selectTransactionsByAmountRange(Prize prize);

    /**
     *获取win_transactions表中的最大轮次
     *
     * @return 最大轮次
     */
    public int getMaxTime();

    /**
     * 检查transactions是否存在客户信息
     *
     * @param custIsn 客户标内码
     * @return true表示存在，false表示不存在
     */
    public boolean existsCust(String custIsn);

    /**
     * 检查transactions是否存在网点信息
     *
     * @param orgNo 网点号
     * @return true表示存在，false表示不存在
     */
    public boolean existsOrgNo(String orgNo);

    /**
     * 获取org1_max_win表中指定支行的最大中奖状态
     *
     * @param org1Code 支行号
     * @return 0表示未中奖，1表示已中奖
     */
    public int getOrg1Status(String org1Code);

    /**
     * 更新org1_max_win表中指定支行的最大中奖状态为已中奖（1）
     * @param org1Code 支行号
     */
    public void updateOrg1MaxWinStatusTo0(String org1Code);
    public void updateOrg1MaxWinStatusTo1(String org1Code);
    public void updateOrg1MaxWinStatusTo2(String org1Code);

    /**
     * 根据交易序号删除transactions表中的记录
     *
     * @param xtranno 交易序号
     */
    public void deleteTransactionsByXtranno(Long xtranno);

    /**
     * 新增中奖记录到win_transactions表
     * 表的第一列记录当前轮次time
     *
     * @param transactions 交易记录
     */
    public void insertWinTransactions(WinTransactions transactions);
}
