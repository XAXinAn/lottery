package com.ruoyi.lottery.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ruoyi.lottery.domain.Prize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.lottery.mapper.TransactionsMapper;
import com.ruoyi.lottery.domain.Transactions;
import com.ruoyi.lottery.service.ITransactionsService;

/**
 * 信用卡交易记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-07-24
 */
@Service
public class TransactionsServiceImpl implements ITransactionsService
{
    @Autowired
    private TransactionsMapper transactionsMapper;

    /**
     * 查询信用卡交易记录
     *
     * @param acctnbr 信用卡交易记录主键
     * @return 信用卡交易记录
     */
    @Override
    public Transactions selectTransactionsByAcctnbr(Long acctnbr)
    {
        return transactionsMapper.selectTransactionsByAcctnbr(acctnbr);
    }

    /**
     * 查询信用卡交易记录列表
     *
     * @param transactions 信用卡交易记录
     * @return 信用卡交易记录
     */
    @Override
    public List<Transactions> selectTransactionsList(Transactions transactions)
    {
        return transactionsMapper.selectTransactionsList(transactions);
    }

    /**
     * 新增信用卡交易记录
     *
     * @param transactions 信用卡交易记录
     * @return 结果
     */
    @Override
    public int insertTransactions(Transactions transactions)
    {
        return transactionsMapper.insertTransactions(transactions);
    }

    /**
     * 修改信用卡交易记录
     *
     * @param transactions 信用卡交易记录
     * @return 结果
     */
    @Override
    public int updateTransactions(Transactions transactions)
    {
        return transactionsMapper.updateTransactions(transactions);
    }

    /**
     * 批量删除信用卡交易记录
     *
     * @param acctnbrs 需要删除的信用卡交易记录主键
     * @return 结果
     */
    @Override
    public int deleteTransactionsByAcctnbrs(Long[] acctnbrs)
    {
        return transactionsMapper.deleteTransactionsByAcctnbrs(acctnbrs);
    }

    /**
     * 删除信用卡交易记录信息
     *
     * @param acctnbr 信用卡交易记录主键
     * @return 结果
     */
    @Override
    public int deleteTransactionsByAcctnbr(Long acctnbr)
    {
        return transactionsMapper.deleteTransactionsByAcctnbr(acctnbr);
    }

    /**
     * 客群：信用卡开卡日期为2025年，客户为90后
     * 要求:每次50个，每次预算3000元，按周提限名单
     * 取当年消费交易其中一笔给予免单，区间为5-800元，100及以上有10个名额，50-100有15个名额，5-50有25个名额（左闭右开）
     * 名额平均到每个网点，网点客户不能连续两期无客户中奖，最高奖每个支行限一次，每个客户限一次。
     * 金额区间 名额 预算
     * 5-50    35  700
     * 50-100  10  800
     * 100-800 5   1500
     *
     * @param prizes 奖项信息
     * @return 结果
     */
    @Override
    public List<Transactions> lottery(List<Prize> prizes) {
        int size = 0;
        int num = 0;
        int budget = 0;
        //取出所有的交易记录
        List<Transactions> transactions =  transactionsMapper.selectAllTransactions();
        //根据金额区间分组
        List<List<Transactions>> transactionsGroupbyAmountRange= new ArrayList<>();
        for (Prize prize : prizes) {
            transactionsGroupbyAmountRange.add(transactionsMapper.selectTransactionsByAmountRange(prize));
        }
        for (List<Transactions> transactionsList : transactionsGroupbyAmountRange) {
            List<Transactions> shuffledList = new ArrayList<>(transactionsList);
            Collections.shuffle(shuffledList);
        }
        return transactionsGroupbyAmountRange.get(0);
    }
}
