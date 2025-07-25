package com.ruoyi.lottery.service.impl;

import java.math.BigDecimal;
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
        //获得当前的轮次
        int time = transactionsMapper.getMaxTime() + 1;
        if(time == 1) {
            int size = prizes.size();
            //根据金额区间分组
            List<List<Transactions>> transactionsGroupbyAmountRange = new ArrayList<>();

            for (Prize prize : prizes) {
                //创建返回值对象
                List<Transactions> result = new ArrayList<>();
                List<BigDecimal> billAmts = new ArrayList<>();
                //取出金额区间内的交易记录
                List<Transactions> transactionsList = transactionsMapper.selectTransactionsByAmountRange(prize);
                //随机打乱交易记录
                do {
                    List<Transactions> shuffledList = new ArrayList<>(transactionsList);
                    Collections.shuffle(shuffledList);
                    int cnt = 0;
                    for (int i = 0; i < transactionsList.size() && cnt < prize.getNum(); i++) {
                        Transactions transaction = shuffledList.get(i);
                        //判断是否符合中奖 客户内码不存在于所有中奖纪录 网点号不存在于本次中奖记录
                        if (!transactionsMapper.existsCust(transaction.getCustIsn())&&!transactionsMapper.existsOrgNo(transaction.getOrgNo())) {
                            if(transaction.getBillAmt().doubleValue() >= prizes.getLast().getMinAmount() && transaction.getBillAmt().doubleValue() < prizes.getLast().getMaxAmount() && (transactionsMapper.getOrg1Status(transaction.getOrg1Code()) == 0)) {
                                winPrize(transaction,time);
                                transactionsMapper.updateOrg1MaxWinStatus(transaction.getOrg1Code());//支行的最大中奖状态设为1
                                cnt++;
                            } else if (!(transaction.getBillAmt().doubleValue() >= prizes.getLast().getMinAmount() && transaction.getBillAmt().doubleValue() < prizes.getLast().getMaxAmount())) {
                                winPrize(transaction,time);
                                cnt++;
                            }
                        }
                    }
                } while (true);
            }
            return null;
        }
        return null;
    }

    /**
     * 中奖处理
     *
     * @param transaction 交易对象
     */
    public void winPrize(Transactions transaction,int time) {
        transactionsMapper.insertWinTransactions(transaction,time);//新增中奖记录
        transactionsMapper.deleteTransactionsByXtranno(transaction.getXtranno());//删除transactions表中的交易记录
    }

    /**
     * 将账单金额列表转换为字符串并打印出来
     *
     * @param billAmts 账单金额列表，每个金额以BigDecimal类型表示
     */
    public void print(List<BigDecimal> billAmts){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < billAmts.size(); i++) {
            sb.append(billAmts.get(i));
            if (i < billAmts.size() - 1) {
                sb.append(", ");
            }
        }
        String result = sb.toString();
        System.out.println(result);
    }

    /**
     * 将账单金额列表相加输出总金额
     *
     * @param billAmts 账单金额列表，每个金额以BigDecimal类型表示
     */
    public double getBillAmtsSum(List<BigDecimal> billAmts) {
        if (billAmts == null || billAmts.isEmpty()) {
            return 0.0; // 如果列表为空或null，返回0.0
        }

        BigDecimal sum = BigDecimal.ZERO; // 初始化总和为0
        for (BigDecimal amount : billAmts) {
            sum = sum.add(amount); // 将每个金额加到总和中
        }

        return sum.doubleValue(); // 返回总和的double值
    }
}
