import request from '@/utils/request'

// 查询信用卡交易记录列表
export function listTransactions(query) {
  return request({
    url: '/lottery/transactions/list',
    method: 'get',
    params: query
  })
}

// 查询信用卡交易记录详细
export function getTransactions(acctnbr) {
  return request({
    url: '/lottery/transactions/' + acctnbr,
    method: 'get'
  })
}

// 新增信用卡交易记录
export function addTransactions(data) {
  return request({
    url: '/lottery/transactions',
    method: 'post',
    data: data
  })
}

// 修改信用卡交易记录
export function updateTransactions(data) {
  return request({
    url: '/lottery/transactions',
    method: 'put',
    data: data
  })
}

// 删除信用卡交易记录
export function delTransactions(acctnbr) {
  return request({
    url: '/lottery/transactions/' + acctnbr,
    method: 'delete'
  })
}

// 抽奖接口
export function lotteryTransactions() {
  return request({
    url: '/lottery/transactions/lottery',
    method: 'get'
  })
}
