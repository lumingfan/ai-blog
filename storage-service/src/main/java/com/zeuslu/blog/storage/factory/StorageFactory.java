package com.zeuslu.blog.storage.factory;

import com.zeuslu.blog.storage.config.StorageConfig;
import com.zeuslu.blog.storage.enums.StorageTypeEnum;
import com.zeuslu.blog.storage.util.StorageStrategy;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 创建存储容器的工厂
 * @author lumingfan
 */
public class StorageFactory {
    private final Map<StorageTypeEnum, StorageStrategy> storageStrategyMap;
    private final StorageConfig storageConfig;

    public StorageFactory(List<StorageStrategy> storageStrategies, StorageConfig storageConfig) {
        this.storageStrategyMap = storageStrategies.stream()
                .collect(Collectors.toMap(StorageStrategy::getType, strategy -> strategy));
        this.storageConfig = storageConfig;
    }

    public StorageStrategy getStorageService() {
        StorageTypeEnum type = storageConfig.getType();
        return storageStrategyMap.get(type);
    }
}