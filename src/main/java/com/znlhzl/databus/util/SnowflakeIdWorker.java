package com.znlhzl.databus.util;

/**
 * TODO optimize
 * <p>
 * Twitter_Snowflake
 */
public class SnowflakeIdWorker {

	// (2018-05-14 23:59:59)时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
	private final static long twepoch = 1526313600000L;
	// 机器标识位数
	private final static long workerIdBits = 5L;
	// 数据中心标识位数
	private final static long datacenterIdBits = 5L;
	// 机器ID最大值
	private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 数据中心ID最大值
	private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	// 毫秒内自增位
	private final static long sequenceBits = 12L;
	// 机器ID偏左移12位
	private final static long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private final static long datacenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
	private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
	/* 上次生产id时间戳 */
	private static long lastTimestamp = -1L;
	// 0，并发控制
	private long sequence = 0L;
	private final long workerId;
	// 数据标识id部分
	private final long datacenterId;

	/**
	 * 构造函数
	 *
	 * @param workerId     工作ID (0~31)
	 * @param datacenterId 数据中心ID (0~31)
	 */
	public SnowflakeIdWorker(long workerId, long datacenterId) {
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	/**
	 * 获得下一个ID (该方法是线程安全的)
	 *
	 * @return SnowflakeId
	 */
	public synchronized long nextId() {
		long timestamp = timeGen();

		//如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(
					String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
		}

		//如果是同一时间生成的，则进行毫秒内序列
		if (lastTimestamp == timestamp) {
			sequence = (sequence + 1) & sequenceMask;
			//毫秒内序列溢出
			if (sequence == 0) {
				//阻塞到下一个毫秒,获得新的时间戳
				timestamp = tilNextMillis(lastTimestamp);
			}
		}
		//时间戳改变，毫秒内序列重置
		else {
			sequence = 0L;
		}

		//上次生成ID的时间截
		lastTimestamp = timestamp;

		//移位并通过或运算拼到一起组成64位的ID
		return ((timestamp - twepoch) << timestampLeftShift) //
				| (datacenterId << datacenterIdShift) //
				| (workerId << workerIdShift) //
				| sequence;
	}

	/**
	 * 阻塞到下一个毫秒，直到获得新的时间戳
	 *
	 * @param lastTimestamp 上次生成ID的时间截
	 * @return 当前时间戳
	 */
	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 返回以毫秒为单位的当前时间
	 *
	 * @return 当前时间(毫秒)
	 */
	protected long timeGen() {
		return System.currentTimeMillis();
	}

}