package com.dango.messagesdk.service.impl;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author dango
 * @description
 * @date
 */
@SpringBootTest
class MessageProcessClassTest {
    @Autowired
    MessageProcessClass messageProcessClass;

    @SneakyThrows
    @Test
    public void test() {

        System.out.println("开始执行-----》" + LocalDateTime.now());
        messageProcessClass.process(0, 1, "test", 5, 30);
        System.out.println("结束执行-----》" + LocalDateTime.now());
        Thread.sleep(9000000);
    }

}