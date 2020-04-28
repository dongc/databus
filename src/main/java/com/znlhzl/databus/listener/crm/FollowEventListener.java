package com.znlhzl.databus.listener.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.maihaoche.starter.mq.base.MessageBuilder;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import com.znlhzl.databus.common.MQTopic;
import com.znlhzl.databus.entity.MetaDataChange;
import com.znlhzl.databus.listener.IEventListener;
import com.znlhzl.databus.mq.producer.ImsProducer;
import com.znlhzl.databus.task.MetaOperateResultEnum;
import com.znlhzl.databus.task.MetaOperateTypeEnum;
import com.znlhzl.databus.task.handler.MetaSubsequentHandler;
import com.znlhzl.databus.util.MetaDataUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * '跟进记录表 '数据变化监听
 * 
 * <pre>
 * destination：crm
 * schema: crm
 * table: CRM_FOLLOW
 * bizPK(业务主键): follow_code
 * </pre>
 *
 * @author dongchuang
 */
@Slf4j
@CanalEventListener
public class FollowEventListener implements IEventListener {

    @Autowired
    ImsProducer producer;
    @Autowired
    MetaSubsequentHandler metaFailSubsequentHandler;

    /**
     * destination
     */
    private static final String DESTINATION = "crm";
    /**
     * schema
     */
    private static final String SCHEMA = "crm";
    /**
     * table
     */
    private static final String TABLE = "crm_follow";
    /**
     * 业务主键字段名
     */
    private static final String bizPK = "follow_code";

    /**
     * 监听新增记录
     *
     * @param rowData
     */
    @Override
    @ListenPoint(destination = DESTINATION, schema = SCHEMA, table = {TABLE}, eventType = CanalEntry.EventType.INSERT)
    public void onInsert(CanalEntry.RowData rowData) {

        // 1.获取列变更明细
        MetaDataChange detail =
            MetaDataUtil.getAfterColumns(SCHEMA, TABLE, rowData, CanalEntry.EventType.INSERT, bizPK);
        // 变更流水号
        String flowNo = detail.getFlowNo();
        // 业务主键
        String recordPK = detail.getBizNo();
        if (StringUtils.isEmpty(recordPK)) {
            log.error("[FOLLOW:ADD] 流水号:{} 业务主键:{} 消费失败，业务主键不存在", flowNo, recordPK);
            return;
        }
        // 变更明细
        String detailJSON = JSON.toJSONString(detail);

        log.info("[FOLLOW:ADD] 流水号:{} 业务主键:{} 变更明细:{}", flowNo, recordPK, detail);

        // 2.发送MQ信息
        try {
            producer.syncSend(MessageBuilder.of(detail).topic(MQTopic.FOLLOW)
                .tag(CanalEntry.EventType.INSERT.name().toLowerCase()).build());
            log.info("[FOLLOW:ADD] 流水号:{} MQ发送成功", flowNo);
            // 元数据消费记录日志
            metaFailSubsequentHandler.record(MetaOperateTypeEnum.GATHER, MetaOperateResultEnum.SUCCESS, detail);
        } catch (Exception e) {
            log.error("[FOLLOW:ADD] 流水号:{} 变更明细:{} MQ发送异常 异常信息:{}", flowNo, detailJSON, e);
            // 元数据消费记录日志
            metaFailSubsequentHandler.record(MetaOperateTypeEnum.GATHER, MetaOperateResultEnum.FAIL, detail);
        }
    }

    /**
     * 监听更新记录
     *
     * @param rowData
     */
    @Override
    @ListenPoint(destination = DESTINATION, schema = SCHEMA, table = {TABLE}, eventType = CanalEntry.EventType.UPDATE)
    public void onUpdate(CanalEntry.RowData rowData) {

        // 1.获取列变更明细
        MetaDataChange detail =
            MetaDataUtil.getBeforeColumns(SCHEMA, TABLE, rowData, CanalEntry.EventType.UPDATE, bizPK);
        // 变更流水号
        String flowNo = detail.getFlowNo();
        // 业务主键
        String recordPK = detail.getBizNo();
        if (StringUtils.isEmpty(recordPK)) {
            log.error("[FOLLOW:UPDATE] 流水号:{} 业务主键:{} 消费失败，业务主键不存在。", flowNo, recordPK);
            return;
        }
        // 变更明细
        String detailJSON = JSON.toJSONString(detail);
        log.info("[FOLLOW:UPDATE] 流水号:{} 业务主键:{} 变更明细:{}", flowNo, recordPK, detail);

        // 2.发送MQ信息
        try {
            producer.syncSend(MessageBuilder.of(detail).topic(MQTopic.FOLLOW)
                .tag(CanalEntry.EventType.UPDATE.name().toLowerCase()).build());
            log.info("[FOLLOW:UPDATE] 流水号:{} 成功发送MQ", flowNo);
            // 元数据消费记录日志
            metaFailSubsequentHandler.record(MetaOperateTypeEnum.GATHER, MetaOperateResultEnum.SUCCESS, detail);
        } catch (Exception e) {
            log.error("[FOLLOW:UPDATE] 流水号:{} 变更明细:{} MQ发送异常 异常信息:{}", flowNo, detailJSON, e);
            // 元数据消费记录日志
            metaFailSubsequentHandler.record(MetaOperateTypeEnum.GATHER, MetaOperateResultEnum.FAIL, detail);
        }
    }

    /**
     * 监听删除记录
     *
     * @param rowData
     */
    @Override
    @ListenPoint(destination = DESTINATION, schema = SCHEMA, table = {TABLE}, eventType = CanalEntry.EventType.DELETE)
    public void onDelete(CanalEntry.RowData rowData) {

        // 1.获取列变更明细
        MetaDataChange detail =
            MetaDataUtil.getBeforeColumns(SCHEMA, TABLE, rowData, CanalEntry.EventType.DELETE, bizPK);
        // 变更流水号
        String flowNo = detail.getFlowNo();
        // 业务主键
        String recordPK = detail.getBizNo();
        if (StringUtils.isEmpty(recordPK)) {
            log.error("[FOLLOW:DELETE] 流水号:{} 业务主键:{} 消费失败，业务主键不存在。", flowNo, recordPK);
            return;
        }
        // 变更明细
        String detailJSON = JSON.toJSONString(detail);
        log.info("[FOLLOW:DELETE] 流水号:{} 业务主键:{} 变更明细:{}", flowNo, recordPK, detail);

        // 2.发送MQ信息
        try {
            producer.syncSend(MessageBuilder.of(detail).topic(MQTopic.FOLLOW)
                .tag(CanalEntry.EventType.DELETE.name().toLowerCase()).build());
            log.info("[FOLLOW:DELETE] 流水号:{} 成功发送MQ", flowNo);
            // 元数据消费记录日志
            metaFailSubsequentHandler.record(MetaOperateTypeEnum.GATHER, MetaOperateResultEnum.SUCCESS, detail);
        } catch (Exception e) {
            log.error("[FOLLOW:DELETE] 流水号:{} 变更明细:{} MQ发送异常 异常信息:{}", flowNo, detailJSON, e);
            // 元数据消费记录日志
            metaFailSubsequentHandler.record(MetaOperateTypeEnum.GATHER, MetaOperateResultEnum.FAIL, detail);
        }
    }

}
