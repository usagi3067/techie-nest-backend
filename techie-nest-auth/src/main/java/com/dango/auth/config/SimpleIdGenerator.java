package com.dango.auth.config;

public class SimpleIdGenerator {
    private static long lastTimestamp = -1L;
    private static long sequence = 0L;

    public synchronized static long assignId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & 4095; // 与雪花算法中的序列号同样的处理逻辑
            if (sequence == 0) {
                // 循环等待到下一个毫秒
                while (timestamp <= lastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        // 简化的ID生成逻辑：时间戳直接左移12位后加上序列号
        return (timestamp << 12) | sequence;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(assignId());
        }
    }
}
