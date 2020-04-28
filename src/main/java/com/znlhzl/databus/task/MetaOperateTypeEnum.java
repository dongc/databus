package com.znlhzl.databus.task;

/**
 * 元数据操作类型
 * 
 * @author dongchuang
 */
public enum MetaOperateTypeEnum {

    GATHER("GATHER", "元数据采集"), CONSUMER("CONSUMER", "元数据消费");

    private String key;
    private String val;

    MetaOperateTypeEnum(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }

    public static MetaOperateTypeEnum getFollowFormEnumBykey(String key) {
        for (MetaOperateTypeEnum e : MetaOperateTypeEnum.values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }

    public static MetaOperateTypeEnum getFollowFormEnumByValue(String value) {
        for (MetaOperateTypeEnum e : MetaOperateTypeEnum.values()) {
            if (e.getVal().equals(value)) {
                return e;
            }
        }

        return null;
    }

}
