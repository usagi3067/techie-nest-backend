package com.dango.content.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-04-05
 */
@Data
public class HomePageDisplayDto {

    /**
     * 最新 id 10个
     */
    List<Long> newIds;

    /**
     * 付费热榜 id 10个
     */
    List<Long>  HotPaidIds;

    /**
     * 免费热榜 id 10个
     */
    List<Long> HotFreeIds;
}
