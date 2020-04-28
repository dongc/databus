package com.znlhzl.databus.task.handler;

import com.znlhzl.databus.entity.MetaDataChange;
import com.znlhzl.databus.entity.MetadataConsume;
import com.znlhzl.databus.service.IMetadataConsumeService;
import com.znlhzl.databus.task.MetaOperateResultEnum;
import com.znlhzl.databus.task.MetaOperateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 拜访记录变更 后续处理任务
 */
@Slf4j
@Component
public class MetaSubsequentHandler {

	@Autowired
	IMetadataConsumeService metadataConsumeService;

	@Async(value = "metalAsync")
	public void record(MetaOperateTypeEnum operateTypeEnum, MetaOperateResultEnum successType, MetaDataChange detail) {

		MetadataConsume metadataConsume = new MetadataConsume();
		// 流水号
		metadataConsume.setFlowNo(detail.getFlowNo());
		// 数据库实例
		metadataConsume.setMschema(detail.getMschema());
		// 表
		metadataConsume.setMtable(detail.getMtable());
		// 记录PK
		metadataConsume.setRecordPk(detail.getBizNo());
		// 变更类型
		metadataConsume.setChangeType(detail.getChangeType().getNumber() + "");
		// 变更时间
		metadataConsume.setChangeDate(new Date());
		// 变更明细
		metadataConsume.setDetail("");
		// 操作类型
		metadataConsume.setOpType(operateTypeEnum.getKey());
		// 操作人(DATABUS)
		metadataConsume.setOperator("DATABUS");
		// 操作时间
		metadataConsume.setOpTime(new Date());
		// 1:成功;2:失败
		metadataConsume.setOpRe(successType.getKey());

		metadataConsumeService.save(metadataConsume);
	}
}
