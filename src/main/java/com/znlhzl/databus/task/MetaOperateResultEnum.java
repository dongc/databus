package com.znlhzl.databus.task;

/**
 * 元数据消费成功ENUM
 * 
 * @author dongchuang
 */
public enum MetaOperateResultEnum {

    SUCCESS("1", "成功"), FAIL("2", "失败");

    private String key;
    private String val;

    MetaOperateResultEnum(String key, String val) {
        this.key = key;
        this.val = val;
    }

    public String getKey() {
        return key;
    }

    public String getVal() {
        return val;
    }

    public static MetaOperateResultEnum getFollowFormEnumBykey(String key) {
        for (MetaOperateResultEnum e : MetaOperateResultEnum.values()) {
            if (e.getKey().equals(key)) {
                return e;
            }
        }
        return null;
    }

    public static MetaOperateResultEnum getFollowFormEnumByValue(String value) {
        for (MetaOperateResultEnum e : MetaOperateResultEnum.values()) {
            if (e.getVal().equals(value)) {
                return e;
            }
        }

        return null;
    }

}
