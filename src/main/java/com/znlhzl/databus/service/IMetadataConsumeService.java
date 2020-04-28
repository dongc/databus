package com.znlhzl.databus.service;

import com.znlhzl.databus.entity.MetadataConsume;

/**
 * 元数据消费service
 * 
 * @author dongchuang
 */
public interface IMetadataConsumeService {

    /**
     * 记录消费流水
     *
     * @param metadataConsume
     */
    void save(MetadataConsume metadataConsume);
}
