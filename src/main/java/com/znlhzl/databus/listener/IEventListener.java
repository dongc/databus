package com.znlhzl.databus.listener;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 表数据变更事件监听
 */
public interface IEventListener {

	/**
	 * 新增
	 *
	 * @param rowData
	 */
	void onInsert(CanalEntry.RowData rowData);

	/**
	 * 修改
	 *
	 * @param rowData
	 */
	void onUpdate(CanalEntry.RowData rowData);

	/**
	 * 删除
	 *
	 * @param rowData
	 */
	void onDelete(CanalEntry.RowData rowData);
}
