package com.znlhzl.databus.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "databus_metadata_consume")
public class MetadataConsume {
	/**
	 * 序号
	 */
	@Id
	@Column(name = "NO")
	private Integer no;

	/**
	 * 标识每笔变更记录
	 */
	@Column(name = "FLOW_NO")
	private String flowNo;

	/**
	 * 实例
	 */
	@Column(name = "MSCHEMA")
	private String mschema;

	/**
	 * 表
	 */
	@Column(name = "MTABLE")
	private String mtable;

	/**
	 * 记录PK
	 */
	@Column(name = "RECORD_PK")
	private String recordPk;

	/**
	 * 变更类型
	 */
	@Column(name = "CHANGE_TYPE")
	private String changeType;

	/**
	 * 变更时间
	 */
	@Column(name = "CHANGE_DATE")
	private Date changeDate;

	/**
	 * 变更明细
	 */
	@Column(name = "DETAIL")
	private String detail;

	@Column(name = "OP_TYPE")
	private String opType;

	@Column(name = "OPERATOR")
	private String operator;

	@Column(name = "OP_TIME")
	private Date opTime;

	/**
	 * 1:成功;2:失败
	 */
	@Column(name = "OP_RE")
	private String opRe;

	/**
	 * 获取序号
	 *
	 * @return NO - 序号
	 */
	public Integer getNo() {
		return no;
	}

	/**
	 * 设置序号
	 *
	 * @param no 序号
	 */
	public void setNo(Integer no) {
		this.no = no;
	}

	/**
	 * 获取标识每笔变更记录
	 *
	 * @return FLOW_NO - 标识每笔变更记录
	 */
	public String getFlowNo() {
		return flowNo;
	}

	/**
	 * 设置标识每笔变更记录
	 *
	 * @param flowNo 标识每笔变更记录
	 */
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}

	/**
	 * 获取实例
	 *
	 * @return MSCHEMA - 实例
	 */
	public String getMschema() {
		return mschema;
	}

	/**
	 * 设置实例
	 *
	 * @param mschema 实例
	 */
	public void setMschema(String mschema) {
		this.mschema = mschema;
	}

	/**
	 * 获取表
	 *
	 * @return MTABLE - 表
	 */
	public String getMtable() {
		return mtable;
	}

	/**
	 * 设置表
	 *
	 * @param mtable 表
	 */
	public void setMtable(String mtable) {
		this.mtable = mtable;
	}

	/**
	 * 获取记录PK
	 *
	 * @return RECORD_PK - 记录PK
	 */
	public String getRecordPk() {
		return recordPk;
	}

	/**
	 * 设置记录PK
	 *
	 * @param recordPk 记录PK
	 */
	public void setRecordPk(String recordPk) {
		this.recordPk = recordPk;
	}

	/**
	 * 获取变更类型
	 *
	 * @return CHANGE_TYPE - 变更类型
	 */
	public String getChangeType() {
		return changeType;
	}

	/**
	 * 设置变更类型
	 *
	 * @param changeType 变更类型
	 */
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	/**
	 * 获取变更时间
	 *
	 * @return CHANGE_DATE - 变更时间
	 */
	public Date getChangeDate() {
		return changeDate;
	}

	/**
	 * 设置变更时间
	 *
	 * @param changeDate 变更时间
	 */
	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}

	/**
	 * 获取变更明细
	 *
	 * @return DETAIL - 变更明细
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * 设置变更明细
	 *
	 * @param detail 变更明细
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return OP_TYPE
	 */
	public String getOpType() {
		return opType;
	}

	/**
	 * @param opType
	 */
	public void setOpType(String opType) {
		this.opType = opType;
	}

	/**
	 * @return OPERATOR
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * @param operator
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return OP_TIME
	 */
	public Date getOpTime() {
		return opTime;
	}

	/**
	 * @param opTime
	 */
	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	/**
	 * 获取1:成功;2:失败
	 *
	 * @return OP_RE - 1:成功;2:失败
	 */
	public String getOpRe() {
		return opRe;
	}

	/**
	 * 设置1:成功;2:失败
	 *
	 * @param opRe 1:成功;2:失败
	 */
	public void setOpRe(String opRe) {
		this.opRe = opRe;
	}
}