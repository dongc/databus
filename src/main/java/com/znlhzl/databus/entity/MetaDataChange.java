package com.znlhzl.databus.entity;

import com.alibaba.otter.canal.protocol.CanalEntry;
import lombok.Data;

import java.io.Serializable;

/**
 * 元数据.变更明细表
 * 
 * @author dongchuang
 */
@Data
public class MetaDataChange implements Serializable {

    private static final long serialVersionUID = -2387808422780862768L;

    /**
     * schema
     */
    private String mschema;
	/**
	 * 表名
	 */
	private String mtable;
	/**
	 * 记录主键
	 */
	private String bizNo;
	/**
	 * 变更流水号
	 */
	private String flowNo;
	/**
	 * 变更类型
	 */
	private CanalEntry.EventType changeType;
	/**
	 * 变更明细
	 */
	private String detail;
	/**
	 * 变更时间
	 */
	private String changeDate;
}
