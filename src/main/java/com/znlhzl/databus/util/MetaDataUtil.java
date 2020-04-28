package com.znlhzl.databus.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.znlhzl.databus.entity.MetaDataChange;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 元数据工具类
 * @author dongchuang
 */
public final class MetaDataUtil {

	// snowflake
	public static final SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);


	/**
	 * 包装行变更后的数据列
	 *
	 * @param SCHEMA        schema实例
	 * @param TABLE         表
	 * @param rowData       行数据
	 * @param eventTypeEnum 行事件
	 * @param bizPkName     行业务主键
	 * @return
	 */
	public static MetaDataChange getAfterColumns(String SCHEMA,
	                                             String TABLE,
	                                             CanalEntry.RowData rowData,
	                                             CanalEntry.EventType eventTypeEnum,
	                                             String bizPkName) {
		// 获取行的column集合
		Map<String, String> columnsMap = getAfterColumns(rowData, bizPkName);

		// 表变更明细
		MetaDataChange detail = wrapperChangeDetail(SCHEMA, TABLE, eventTypeEnum, bizPkName, columnsMap);

		return detail;
	}


	/**
	 * 包装行变更前的数据列
	 *
	 * @param SCHEMA        schema实例
	 * @param TABLE         表
	 * @param rowData       行数据
	 * @param eventTypeEnum 行事件
	 * @param bizPkName     行业务主键
	 * @return
	 */
	public static MetaDataChange getBeforeColumns(String SCHEMA,
	                                              String TABLE,
	                                              CanalEntry.RowData rowData,
	                                              CanalEntry.EventType eventTypeEnum,
	                                              String bizPkName) {

		// 获取行的column集合
		Map<String, String> columnsMap = getBeforeColumns(rowData, bizPkName);

		// 表变更明细
		MetaDataChange detail = wrapperChangeDetail(SCHEMA, TABLE, eventTypeEnum, bizPkName, columnsMap);

		return detail;
	}


	/**
	 * 获取表字段集合
	 *
	 * @param rowData   行数据
	 * @param bizPkName 行业务主键
	 * @return
	 */
	private static Map<String, String> getBeforeColumns(CanalEntry.RowData rowData, String bizPkName) {
		List<CanalEntry.Column> columns = rowData.getBeforeColumnsList();
		Map<String, String> columnsMap = new HashMap<>(columns.size());
		String columnName = null;
		String columnValue = null;
		for (CanalEntry.Column column : columns) {
			columnName = column.getName();
			columnValue = column.getValue();
			if (bizPkName.equals(columnName)) {
				// 业务主键
				columnsMap.put(bizPkName, columnValue);
			} else {
				columnsMap.put(columnName, columnValue);
			}
		}

		return columnsMap;
	}


	/**
	 * 获取表字段集合
	 *
	 * @param rowData   行数据
	 * @param bizPkName 行业务主键
	 * @return
	 */
	private static Map<String, String> getAfterColumns(CanalEntry.RowData rowData, String bizPkName) {
		List<CanalEntry.Column> columns = rowData.getAfterColumnsList();
		Map<String, String> columnsMap = new HashMap<>(columns.size());
		String columnName = null;
		String columnValue = null;
		for (CanalEntry.Column column : columns) {
			columnName = column.getName();
			columnValue = column.getValue();
			if (bizPkName.equals(columnName)) {
				// 业务主键
				columnsMap.put(bizPkName, columnValue);
			} else {
				columnsMap.put(columnName, columnValue);
			}
		}

		return columnsMap;
	}

	/**
	 * 业务流水号
	 *
	 * @param TABLE     表名
	 * @param eventType 事件名称
	 * @return
	 */
	private static String generateFlowNo(String TABLE, String eventType) {
		StringBuilder changeFlowNo = new StringBuilder();
		changeFlowNo.append(TABLE);
		changeFlowNo.append("_");
		changeFlowNo.append(eventType);
		changeFlowNo.append("_");
		changeFlowNo.append(idWorker.nextId());

		return changeFlowNo.toString();
	}


	/**
	 * 包装变更明细
	 *
	 * @param SCHEMA        实例
	 * @param TABLE         表
	 * @param eventTypeEnum 变更事件类型
	 * @param bizPkName     业务主键
	 * @param columnsMap    列集合
	 * @return
	 */
	private static MetaDataChange wrapperChangeDetail(String SCHEMA,
	                                                  String TABLE,
	                                                  CanalEntry.EventType eventTypeEnum,
	                                                  String bizPkName,
	                                                  Map<String, String> columnsMap) {
		// 元数据变更明细
		MetaDataChange detail = new MetaDataChange();
		// shema
		detail.setMschema(SCHEMA);
		// table
		detail.setMtable(TABLE);
		// 业务主键
		detail.setBizNo(columnsMap.get(bizPkName));
		// 变更业务流水编号
		detail.setFlowNo(generateFlowNo(TABLE, eventTypeEnum.name().toLowerCase()));
		// 变更类型
		detail.setChangeType(eventTypeEnum);
		// 变更明细
		detail.setDetail(JSON.toJSONString(columnsMap));
		// 变更日期
		detail.setChangeDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss")));

		return detail;
	}

}
