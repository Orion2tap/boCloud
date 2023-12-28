/**
 * Copyright (C) 2018-2023
 * All rights reserved, Designed By www.flma.tech
 * 注意：
 * 本软件为www.flma.tech开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package tech.flma.modules.devicedatas.strategy;

import tech.flma.modules.devicedatas.strategy.impl.TyreTreadDepthParsingStrategy;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略上下文
 * @author flma
 * @date 2023-11-10
 */
public class DataParsingContext {
    private Map<String, DataParsingStrategy> strategyMap;

    public DataParsingContext() {
        this.strategyMap = new HashMap<>();
        // 注册策略
        this.strategyMap.put("DW150", new TyreTreadDepthParsingStrategy());
    }

    public Object parse(String valueObj, String deviceType) {
        // 根据设备型号 动态选择解析策略
        DataParsingStrategy strategy = strategyMap.get(deviceType);
        return strategy.parse(valueObj);
    }
}


