package com.ruoyi.lottery.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.lottery.domain.Prize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.lottery.domain.Transactions;
import com.ruoyi.lottery.service.ITransactionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 信用卡交易记录Controller
 *
 * @author ruoyi
 * @date 2025-07-24
 */
@RestController
@RequestMapping("/lottery/transactions")
public class TransactionsController extends BaseController
{
    @Autowired
    private ITransactionsService transactionsService;

    /**
     * 查询信用卡交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('lottery:transactions:list')")
    @GetMapping("/list")
    public TableDataInfo list(Transactions transactions)
    {
        startPage();
        List<Transactions> list = transactionsService.selectTransactionsList(transactions);
        return getDataTable(list);
    }

    /**
     * 导出信用卡交易记录列表
     */
    @PreAuthorize("@ss.hasPermi('lottery:transactions:export')")
    @Log(title = "信用卡交易记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Transactions transactions)
    {
        List<Transactions> list = transactionsService.selectTransactionsList(transactions);
        ExcelUtil<Transactions> util = new ExcelUtil<Transactions>(Transactions.class);
        util.exportExcel(response, list, "信用卡交易记录数据");
    }

    /**
     * 获取信用卡交易记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('lottery:transactions:query')")
    @GetMapping(value = "/{acctnbr}")
    public AjaxResult getInfo(@PathVariable("acctnbr") Long acctnbr)
    {
        return success(transactionsService.selectTransactionsByAcctnbr(acctnbr));
    }

    /**
     * 新增信用卡交易记录
     */
    @PreAuthorize("@ss.hasPermi('lottery:transactions:add')")
    @Log(title = "信用卡交易记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Transactions transactions)
    {
        return toAjax(transactionsService.insertTransactions(transactions));
    }

    /**
     * 修改信用卡交易记录
     */
    @PreAuthorize("@ss.hasPermi('lottery:transactions:edit')")
    @Log(title = "信用卡交易记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Transactions transactions)
    {
        return toAjax(transactionsService.updateTransactions(transactions));
    }

    /**
     * 删除信用卡交易记录
     */
    @PreAuthorize("@ss.hasPermi('lottery:transactions:remove')")
    @Log(title = "信用卡交易记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{acctnbrs}")
    public AjaxResult remove(@PathVariable Long[] acctnbrs)
    {
        return toAjax(transactionsService.deleteTransactionsByAcctnbrs(acctnbrs));
    }

    @GetMapping("/lottery")
    public AjaxResult lottery() {
//        List<Transactions> transactionsList = new ArrayList<>();
//        Transactions transaction = new Transactions(9234167L,20250117L,19451778L,731282L, BigDecimal.valueOf(66.3),"966230","966230","7dcee2c765c9b735ded5b1c21e49a566","正常");
//        transactionsList.add(transaction);
        List<Prize> prizes = new ArrayList<>();
        prizes.add(new Prize(5,50,35,700));
        prizes.add(new Prize(50,100,10,800));
        prizes.add(new Prize(100,800,5,1500));
        List<Transactions> transactionsList = transactionsService.lottery(prizes);
        return AjaxResult.success(transactionsList);
    }
}
