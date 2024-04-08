package com.dango.auth.config;

public class SnowflakeIdWorker {

    // 开始时间截 (2022-01-01)
    private final long twepoch = 1640995200000L;

    // 机器id所占的位数
    private final long workerIdBits = 5L;

    // 数据标识id所占的位数
    private final long datacenterIdBits = 5L;

    // 支持的最大机器id，结果是31
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    // 支持的最大数据标识id，结果是31
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    // 序列在id中占的位数
    private final long sequenceBits = 12L;

    // 机器ID向左移12位
    private final long workerIdShift = sequenceBits;

    // 数据标识id向左移17位(12+5)
    private final long datacenterIdShift = sequenceBits + workerIdBits;

    // 时间截向左移22位(5+5+12)
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    // 生成序列的掩码，这里为4095
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId;
    private long datacenterId;
    private long sequence = 0L;

    // 上次生成ID的时间截
    private long lastTimestamp = -1L;

    // 构造函数
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

    // 获得下一个ID (该方法是线程安全的)
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明时钟回拨过这个时间戳已经生成过ID
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp); // 序列溢出
            }
        } else {
            sequence = 0L; // 时间戳改变，毫秒内序列重置
        }

        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    // 等待下一个毫秒的到来，直到获得新的时间戳
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    // 返回以毫秒为单位的当前时间
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    // 测试
    public static void main(String[] args) {
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 1);
        for (int i = 0; i < 10; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }
}
