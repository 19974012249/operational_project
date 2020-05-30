//package com.zw;
//
//import org.apache.commons.lang3.tuple.MutablePair;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//import site.neware.euicc.core.comp.cache.config.IParaCacheService;
//import site.neware.euicc.core.comp.mq.OrderCancelMQSender;
//import site.neware.euicc.core.comp.mq.entity.OrderCancelMessage;
//import site.neware.euicc.core.comp.mq.entity.OrderCancelProcessMessage;
//import site.neware.euicc.core.consts.CommonConsts;
//import site.neware.euicc.core.consts.MQConstants;
//import site.neware.euicc.core.exceptions.ServiceCheckException;
//import site.neware.euicc.core.exceptions.ServiceExecutionException;
//import site.neware.euicc.core.manager.order.OrderCancelManager;
//import site.neware.euicc.core.persist.api.Operation;
//import site.neware.euicc.core.persist.order.dao.impl.OrderDao;
//import site.neware.euicc.core.persist.order.dto.BatchTaskDetailDTO;
//import site.neware.euicc.core.persist.order.dto.OrderCancelFileDTO;
//import site.neware.euicc.core.persist.order.entity.*;
//import site.neware.euicc.core.persist.order.entity.enums.*;
//import site.neware.euicc.core.persist.system.batch.entity.BatchFile;
//import site.neware.euicc.core.service.BaseService;
//import site.neware.euicc.core.service.busi.BusinessService;
//import site.neware.euicc.core.service.order.dto.enums.OrderTypeEnums;
//import site.neware.euicc.core.utils.AssertUtils;
//import site.neware.euicc.core.utils.BeanUtils;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class CancelOrderService extends BaseService {
//    private final static Logger LOGGER = LoggerFactory.getLogger(CancelOrderService.class);
//    @Autowired
//    private OrderCancelManager orderCancelManager;
//    @Autowired
//    private OrderCancelMQSender orderCancelMQSender;
//    @Autowired
//    private BusinessService businessService;
//    @Autowired
//    private OrderDao orderDao;
//
//    public void orderCancelFileParsing(OrderCancelMessage message) {
//        String batchId = message.getBatchId();
//        OrderBatchTask batchTask = orderCancelManager.getByBatchId(batchId);
//        //订单任务不存在，流程结束
//        if (batchTask == null) {
//            LOGGER.error("Not exists batchTask info[{}]", batchId);
//            return;
//        }
//
//        //获取当前用户信息
//        Operation operation = getOperation(message);
//        BatchFile batchFile = batchFileDao.selectByPrimaryKey(message.getFileId());
//        //文件不存在，流程结束
//        if (batchFile == null) {
//            LOGGER.error("Order batch task file does not exist[{}]", message.getFileId());
//            failureOrderCancel(operation, batchTask, 0, 0, "Order batch task file does not exist");
//            return;
//        }
//
//        boolean cidFileFlag = OrderCancelSourceEnums.FILE_CID.getValue().equals(batchTask.getSource());
//        //订单数据
//        List<String> dateList = this.orderCancelFileParsing(batchFile, operation, batchTask);
//
//        //数据校验及处理
//        BatchTaskDetailDTO batchTaskDetailDTO = dealDataList(dateList, batchTask, dateList.size(), operation, cidFileFlag);
//
//        //数据入库
//        boolean dataSaveSuccessFlag = saveDateIntoDB(batchTaskDetailDTO, dateList.size(), batchTask, operation);
//
//        //成功后调用取消订单MQ
//        if (dataSaveSuccessFlag) {
//            callingOrderCancelMQ(batchTask);
//        }
//    }
//
//    private boolean saveDateIntoDB(BatchTaskDetailDTO batchTaskDetailDTO, int cidQuantity, OrderBatchTask batchTask, Operation operation) {
//        boolean dataSaveSuccessFlag = true;
//
//        List<OrderBatchTaskDetail> dealDateList = batchTaskDetailDTO.getDealDateList();
//
//        if (AssertUtils.isListEmpty(dealDateList)) {
//            failureOrderCancel(operation, batchTask, 0, 0, "Order batch task data does not exist");
//            return false;
//        }
//
//        //如果校验全部失败则订单取消任务创建失败
//        Integer failCount = batchTaskDetailDTO.getFailCountMap().get("failCount");
//        int size = dealDateList.size();
//        if (failCount == size) {
//            failureOrderCancel(operation, batchTask, 0, cidQuantity, "Data Checked failure");
//            dataSaveSuccessFlag = false;
//        }
//
//        //数据分批入库
//        List<OrderBatchTaskDetail> insertDataList = new ArrayList<>(10);
//        int dataSize = 0;
//        for (OrderBatchTaskDetail orderBatchTaskDetail : dealDateList) {
//            insertDataList.add(orderBatchTaskDetail);
//            dataSize++;
//            //50条入库一次
//            if (dataSize == CommonConsts.RSP_PURCHASE_PER_REQUEST_MAX_QUANTITY) {
//                orderCancelManager.insertDetails(insertDataList);
//                dataSize = 0;
//                insertDataList.clear();
//            }
//        }
//
//        //处理余下的数据
//        if (!AssertUtils.isListEmpty(insertDataList)) {
//            orderCancelManager.insertDetails(insertDataList);
//        }
//
//        if (dataSaveSuccessFlag) {
//            //处理成功后更新批量任务记录
//            successOrderCancel(operation, batchTask, size, cidQuantity);
//        }
//        return dataSaveSuccessFlag;
//    }
//
//    private BatchTaskDetailDTO dealDataList(List<String> dateList, OrderBatchTask batchTask, int cidQuantity, Operation operation,
//                                            boolean cidFileFlag) {
//        if (AssertUtils.isListEmpty(dateList)) {
//            LOGGER.error("Order cancel file parsing content is empty");
//            failureOrderCancel(operation, batchTask, 0, cidQuantity, "Order cancel file parsing content is empty");
//            throw new ServiceCheckException("Order cancel file parsing content is empty");
//        }
//
//        List<OrderBatchTaskDetail> batchTaskDetailList = new ArrayList<>(dateList.size());
//
//        Map<String, Integer> failCountMap = new HashMap<>();
//        failCountMap.put("failCount", 0);
//
//        String defaultGoods = paraCacheService.getSetting(IParaCacheService.GOODS_DEFAULT);
//        if (AssertUtils.isStringEmpty(defaultGoods)) {
//            throw new ServiceCheckException("default goods does not exist");
//        }
//        for (String line : dateList) {
//            List<OrderBatchTaskDetail> taskDetailList;
//            if (cidFileFlag) {
//                taskDetailList = dealCIDDataList(line, batchTask, defaultGoods, operation, failCountMap);
//            } else {
//                taskDetailList = dealCIDAndPIDDataList(line, batchTask, defaultGoods, operation, failCountMap);
//            }
//            if (!AssertUtils.isListEmpty(taskDetailList)) {
//                batchTaskDetailList.addAll(taskDetailList);
//            }
//
//        }
//        BatchTaskDetailDTO dto = new BatchTaskDetailDTO();
//        dto.setFailCountMap(failCountMap);
//        dto.setDealDateList(batchTaskDetailList);
//        return dto;
//    }
//
//    /**
//     * 处理cid|pid文件数据
//     *
//     * @param line
//     * @param batchTask
//     * @param operation
//     * @param failCountMap
//     * @return
//     */
//    private List<OrderBatchTaskDetail> dealCIDAndPIDDataList(String line, OrderBatchTask batchTask, String defaultGoods, Operation operation, Map<String, Integer> failCountMap) {
//        String[] splitString = line.split("\\|");
//        String cid;
//        String goodNo;
//        try {
//            cid = splitString[0].trim();
//            goodNo = splitString[1].trim();
//        } catch (Exception e) {
//            //CID|PID解析失败，跳过此行数据
//            return null;
//        }
//
//        return getOrderBatchTaskDetails(batchTask, defaultGoods, operation, failCountMap, cid, goodNo);
//    }
//
//    /**
//     * 处理cid文件数据
//     *
//     * @param line
//     * @param batchTask
//     * @param operation
//     * @param failCountMap
//     * @return
//     */
//    private List<OrderBatchTaskDetail> dealCIDDataList(String line, OrderBatchTask batchTask, String defaultGoods, Operation operation, Map<String, Integer> failCountMap) {
//        String cid = line.replaceAll("\\|", "").trim();
//
//        return getOrderBatchTaskDetails(batchTask, defaultGoods, operation, failCountMap, cid, null);
//    }
//
//    private List<OrderBatchTaskDetail> getOrderBatchTaskDetails(OrderBatchTask batchTask, String defaultGoods, Operation operation, Map<String, Integer> failCountMap, String cid, String goodNo) {
//        //查询该套餐的订单信息
//        List<OrderCancelFileDTO> packageInfoList = orderDao.selectOrderInfoByCidAndGoodsNo(cid, goodNo);
//
//        List<OrderBatchTaskDetail> batchTaskDetailList = new ArrayList<>(packageInfoList.size());
//
//        if (!AssertUtils.isListEmpty(packageInfoList)) {
//            try {
//                int failureCount = 0;
//                for (OrderCancelFileDTO journal : packageInfoList) {
//
//                    MutablePair<Boolean, String> dataCheckPair = new MutablePair<>();
//                    //默认校验通过
//                    dataCheckPair.setLeft(Boolean.TRUE);
//
//                    if (!journal.getMerchantId().equals(batchTask.getMerchantId())) {
//                        //如果套餐记录不属于该商户，校验不通过，只记录一条失败任务数
//                        throw new ServiceCheckException("The order is not associated with the merchant");
//                    } else if (!OrderStatusEnums.COMPELETED.getEnumValue().equals(journal.getOrderStatus())) {
//                        //如果订单状态不为已完成，校验不通过
//                        dataCheckPair.setLeft(Boolean.FALSE);
//                        dataCheckPair.setRight("Order is not completed : " + journal.getOrderId());
//                    } else if (defaultGoods.equals(journal.getGoodsNo()) && changeOrderActived(journal.getCid(), journal.getOrderId())) {
//                        //如果为激活过的切单，则不能做取消
//                        dataCheckPair.setLeft(Boolean.FALSE);
//                        dataCheckPair.setRight("change order has been activated : " + journal.getOrderId());
//                    } else if (OrderCancelFileDTO.ActivatedStatus.ACTIVATED.equals(journal.getActivatedStatus())) {
//                        //如果套装已激活，校验不通过
//                        dataCheckPair.setLeft(Boolean.FALSE);
//                        if (OrderDetailStatusEnums.NORMAL.getStatus().equals(journal.getOrderDetailStatus())) {
//                            dataCheckPair.setRight("This package is activated : " + journal.getOrderId());
//                        } else {
//                            dataCheckPair.setRight("This package is recovered : " + journal.getOrderId());
//                        }
//                    } else if (OrderCancelFileDTO.OrderType.BATCH_ORDER.equals(journal.getOrderType()) && BatchOrderJournalStatusEnums.CANCELED.getEnumValue().equals(journal.getJournalStatus())) {
//                        //如果订单为批量订购，并且套餐订购状态为已取消，校验不通过
//                        dataCheckPair.setLeft(Boolean.FALSE);
//                        dataCheckPair.setRight("The package is cancelled : " + journal.getOrderId());
//                    }
//
//                    if (!dataCheckPair.getLeft()) {
//                        failureCount++;
//                        //如果校验失败等于总套餐数，则只记录一条失败任务数
//                        if (failureCount == packageInfoList.size()) {
//                            throw new ServiceCheckException("Can't cancel, all package inspection failed");
//                        }
//                        //校验不通过，不记录这个套餐任务信息
//                        continue;
//                    }
//
//                    //校验成功后
//                    OrderBatchTaskDetail taskDetail = new OrderBatchTaskDetail();
//                    String taskDetailId = batchTask.getBatchId() + "-" + sequenceCompService.getNextOrderDetailId();
//                    taskDetail.setDetailId(taskDetailId);
//                    taskDetail.setBatchId(batchTask.getBatchId());
//                    taskDetail.setOrderId(journal.getOrderId());
//                    taskDetail.setCid(journal.getCid());
//                    taskDetail.setGoodsNo(journal.getGoodsNo());
//                    taskDetail.setStatus(OrderCancelStatusEnums.PROCESSING.getEnumValue());
//                    taskDetail.setCreateTime(operation.getOpTime());
//                    taskDetail.setUpdateTime(operation.getOpTime());
//
//                    batchTaskDetailList.add(taskDetail);
//                }
//
//            } catch (Exception e) {
//                batchTaskDetailList.clear();
//                batchTaskDetailList.add(buildFailureOrderBatchTaskDetail(batchTask, operation, failCountMap, e.getMessage(), cid, goodNo));
//            }
//        } else {
//            //该CID或CID|PID没有关联订单
//            batchTaskDetailList.add(buildFailureOrderBatchTaskDetail(batchTask, operation, failCountMap, "Not exists order info", cid, goodNo));
//        }
//        return batchTaskDetailList;
//    }
//
//    private OrderBatchTaskDetail buildFailureOrderBatchTaskDetail(OrderBatchTask batchTask, Operation operation, Map<String, Integer> failCountMap, String remark, String cid, String goodNo) {
//        OrderBatchTaskDetail taskDetail = new OrderBatchTaskDetail();
//        String taskDetailId = batchTask.getBatchId() + "-" + sequenceCompService.getNextOrderDetailId();
//        taskDetail.setDetailId(taskDetailId);
//        taskDetail.setBatchId(batchTask.getBatchId());
//        taskDetail.setCid(cid);
//        taskDetail.setGoodsNo(goodNo);
//        taskDetail.setStatus(OrderCancelStatusEnums.FAILED.getEnumValue());
//        taskDetail.setRemark(remark);
//        taskDetail.setCreateTime(operation.getOpTime());
//        taskDetail.setUpdateTime(operation.getOpTime());
//        failCountMap.put("failCount", failCountMap.get("failCount") + 1);
//        return taskDetail;
//    }
//
//    private List<String> orderCancelFileParsing(BatchFile batchFile, Operation operation, OrderBatchTask batchTask) {
//        String line;
//        // 批量CID
//        List<String> dataList = new ArrayList<>(10 * 1024);
//        try {
//            InputStream input = sftpCompService.download(batchFile.getFilePath(), batchFile.getFileRename());
//            BufferedReader reader = new LineNumberReader(new InputStreamReader(input));
//            while (null != (line = reader.readLine())) {
//                if (AssertUtils.isStringEmpty(line)) {
//                    continue;
//                }
//                dataList.add(line);
//            }
//            return dataList;
//        } catch (IOException e) {
//            failureOrderCancel(operation, batchTask, 0, 0, "Read batch file exception");
//            throw new ServiceExecutionException("Read batch file exception, fileId = " + batchFile.getFileId(), e);
//        }
//
//    }
//
//    private void failureOrderCancel(Operation operation, OrderBatchTask batchTask, int taskQuantity, int cidQuantity, String remark) {
//        batchTask.setStatus(OrderCancelStatusEnums.FAILED.getEnumValue());
//        batchTask.setOpRemark(remark);
//        batchTask.setCidQuantity(cidQuantity);
//        batchTask.setTaskQuantity(taskQuantity);
//        batchTask.setUpdateTime(operation.getOpTime());
//        orderCancelManager.updateBatchTask(batchTask);
//    }
//
//    private Operation getOperation(OrderCancelMessage message) {
//        Operation operation = new Operation();
//        BeanUtils.copyProperties(message, operation);
//        operation.setOpTime(dateTimeDao.selectNowTime());
//        return operation;
//    }
//
//    public void singleOrderCancel(OrderCancelMessage message) {
//        String batchId = message.getBatchId();
//        OrderBatchTask batchTask = orderCancelManager.getByBatchId(batchId);
//        //订单任务不存在，流程结束
//        if (batchTask == null) {
//            LOGGER.error("Not exists batchTask info[{}]", batchId);
//            return;
//        }
//        String defaultGoods = paraCacheService.getSetting(IParaCacheService.GOODS_DEFAULT);
//        if (AssertUtils.isStringEmpty(defaultGoods)) {
//            throw new ServiceCheckException("default goods does not exist");
//        }
//
//        //获取当前用户信息
//        Operation operation = getOperation(message);
//
//        Order order = orderDao.selectById(batchTask.getOrderId());
//        if (order == null) {
//            LOGGER.error("Not exists order info[{}]", batchTask.getOrderId());
//            failureOrderCancel(operation, batchTask, 0, 0, "Not exists order info");
//            return;
//        } else if (!OrderStatusEnums.COMPELETED.getEnumValue().equals(order.getStatus())) {
//            //失败
//            failureOrderCancel(operation, batchTask, 0, 0, "Order is not completed");
//            return;
//        }
//        if (defaultGoods.equals(order.getGoodsNo())) {
//            if (changeOrderActived(order.getCid(), order.getOrderId())) {
//                //失败
//                failureOrderCancel(operation, batchTask, 0, 1, "change order has been activated");
//                return;
//            }
//        }
//        //不是Single方式订购的服务
//        if (order.getCid() == null) {
//            List<BatchOrderJournal> journalList = batchOrderJournalDao.getByOrderId(order.getOrderId());
//
//            //校验是否已订购&未激活的
//            boolean dataSaveSuccessFlag = dealDetailList(batchTask.getBatchId(), journalList, order, operation, batchTask);
//
//            //成功后调用取消订单MQ
//            if (dataSaveSuccessFlag) {
//                callingOrderCancelMQ(batchTask);
//            } else {
//                //失败
//                failureOrderCancel(operation, batchTask, 0, journalList.size(), "Order is activated or recovered");
//            }
//        } else {
//            //是Single方式订购的服务&&订单已完成&&未激活
//            List<OrderDetail> orderDetailList = orderDetailDao.getByOrderIdAndCidAndType(order.getOrderId(), order.getCid(), OrderTypeEnums.SINGLE_SERVICE_PROFILE.getType());
//            if (AssertUtils.isListEmpty(orderDetailList)) {
//                OrderBatchTaskDetail taskDetail = new OrderBatchTaskDetail();
//                String nextOrderDetailId = sequenceCompService.getNextOrderDetailId();
//                String taskDetailId = batchId + "-" + nextOrderDetailId;
//                taskDetail.setDetailId(taskDetailId);
//                taskDetail.setBatchId(batchId);
//                taskDetail.setOrderId(order.getOrderId());
//                taskDetail.setCid(order.getCid());
//                taskDetail.setGoodsNo(order.getGoodsNo());
//                taskDetail.setCreateTime(operation.getOpTime());
//                taskDetail.setUpdateTime(operation.getOpTime());
//                taskDetail.setStatus(OrderCancelStatusEnums.PROCESSING.getEnumValue());
//                orderCancelManager.insertDetail(taskDetail);
//                successOrderCancel(operation, batchTask, 1, 1);
//                callingOrderCancelMQ(batchTask);
//            } else {
//                //失败
//                failureOrderCancel(operation, batchTask, 0, 1, "Order is activated or recovered");
//            }
//        }
//    }
//
//    private boolean dealDetailList(String batchId, List<BatchOrderJournal> detailList, Order order, Operation operation, OrderBatchTask batchTask) {
//        if (CollectionUtils.isEmpty(detailList)) {
//            failureOrderCancel(operation, batchTask, 0, 0, "No package is ordered under this order");
//            return false;
//        }
//
//        //订单类型为服务资源说明已经全部激活
//        List<OrderDetail> orderDetailList = orderDetailDao.getByOrderIdAndCidAndType(order.getOrderId(), null, OrderTypeEnums.SINGLE_SERVICE_PROFILE.getType());
//
//        List<String> activeCidList = new ArrayList<>(orderDetailList.size());
//        for (OrderDetail detail : orderDetailList) {
//            activeCidList.add(detail.getCid());
//        }
//
//        operation.setOpTime(dateTimeDao.selectNowTime());
//        String nextOrderDetailId = sequenceCompService.getNextOrderDetailId();
//
//        List<OrderBatchTaskDetail> list = new ArrayList<>(detailList.size());
//        int dataSize = 0;
//        int total = 0;
//        for (BatchOrderJournal journal : detailList) {
//            if (!AssertUtils.isListEmpty(activeCidList) && activeCidList.contains(journal.getCid())) {
//                continue;
//            }
//
//            //不记录订单下已取消套餐的任务
//            if (!BatchOrderJournalStatusEnums.CANCELED.getEnumValue().equals(journal.getStatus())) {
//                OrderBatchTaskDetail taskDetail = new OrderBatchTaskDetail();
//                String taskDetailId = batchId + "-" + nextOrderDetailId;
//                taskDetail.setDetailId(taskDetailId);
//                taskDetail.setBatchId(batchId);
//                taskDetail.setOrderId(journal.getOrderId());
//                taskDetail.setCid(journal.getCid());
//                taskDetail.setGoodsNo(order.getGoodsNo());
//                taskDetail.setCreateTime(operation.getOpTime());
//                taskDetail.setUpdateTime(operation.getOpTime());
//
//                taskDetail.setStatus(OrderCancelStatusEnums.PROCESSING.getEnumValue());
//
//                dataSize++;
//                list.add(taskDetail);
//
//                //50条入库一次
//                if (dataSize == CommonConsts.RSP_PURCHASE_PER_REQUEST_MAX_QUANTITY) {
//                    orderCancelManager.insertDetails(list);
//                    dataSize = 0;
//                    list.clear();
//                }
//
//                long nextId = Long.decode(nextOrderDetailId);
//                nextId++;
//                nextOrderDetailId = Long.toString(nextId);
//                total++;
//            }
//        }
//
//        //处理余下的数据
//        if (!AssertUtils.isListEmpty(list)) {
//            orderCancelManager.insertDetails(list);
//        }
//        //如果订单下已完成套餐的数量为0，则订单取消任务失败
//        if (total == 0) {
//            failureOrderCancel(operation, batchTask, total, total, "All the packages under the order are incomplete");
//            return false;
//        } else {
//            successOrderCancel(operation, batchTask, total, total);
//            return true;
//        }
//    }
//
//    private void successOrderCancel(Operation operation, OrderBatchTask batchTask, int taskQuantity, int cidQuantity) {
//        batchTask.setStatus(OrderCancelStatusEnums.PROCESSING.getEnumValue());
//        batchTask.setCidQuantity(cidQuantity);
//        batchTask.setTaskQuantity(taskQuantity);
//        batchTask.setUpdateTime(operation.getOpTime());
//        orderCancelManager.updateBatchTask(batchTask);
//    }
//
//    private void callingOrderCancelMQ(OrderBatchTask batchTask) {
//        OrderCancelProcessMessage message = new OrderCancelProcessMessage();
//        if (OrderCancelSourceEnums.ORDER.getValue().equals(batchTask.getSource())) {
//            message.setOrderId(batchTask.getOrderId());
//        }
//        message.setBatchId(batchTask.getBatchId());
//        message.setMerchantId(batchTask.getMerchantId());
//        message.setSource(batchTask.getSource().toString());
//        try {
//            orderCancelMQSender.cancelOrderProcess(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//            LOGGER.error("[x] Send Message error! Action[{}], Data[batchId={}]", MQConstants.OrderCancel.Action.ORDER_CANCEL_PROCESS, message.getBatchId());
//        }
//    }
//n
//    private boolean changeOrderActived(String cid, String orderId) {
//        List<String> actIds = businessService.getChangedActOrders(cid, orderId);
//        if (!AssertUtils.isListEmpty(actIds)) {
//            //已经激活的不能取消
//            return true;
//        }
//        return false;
//    }
//
//}
