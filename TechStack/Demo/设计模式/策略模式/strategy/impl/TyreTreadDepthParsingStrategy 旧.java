/**
 * Copyright (C) 2018-2023
 * All rights reserved, Designed By www.flma.tech
 * 注意：
 * 本软件为www.flma.tech开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package tech.flma.modules.devicedatas.strategy.impl;

import com.fasterxml.jackson.databind.JsonNode;
import tech.flma.modules.devicedatas.constant.DeviceDatasConstants;
import tech.flma.modules.devicedatas.strategy.DataParsingStrategy;
import tech.flma.modules.devicedatas.vo.TyreTreadDepthVo;
import tech.flma.modules.tools.utils.JacksonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 轮胎花纹深度解析策略
 * @author flma
 * @date 2023-11-10
 */
public class TyreTreadDepthParsingStrategy implements DataParsingStrategy {

    @Override
    public Object parse(String valueObj) {
        JsonNode jsonNode = (JsonNode) JacksonUtil.toNode(valueObj);

        // 初始化，设置轮胎数量、单位和凹槽数量
        TyreTreadDepthVo tyreTreadDepthVo = new TyreTreadDepthVo();
        tyreTreadDepthVo.setTyres(jsonNode.get("tyres").asInt());
        tyreTreadDepthVo.setUnit(jsonNode.get("unit").asText());
        tyreTreadDepthVo.setGrooves(jsonNode.get("grooves").asInt());

        // 准备用于存放轮胎数据的Map
        Map<String, TyreTreadDepthVo.TyreData> tyreDataMap = new HashMap<>();
        JsonNode dataNode = jsonNode.get("data");

        // 遍历每个轮胎
        dataNode.fields().forEachRemaining(tyreEntry -> {
            String tyreKey = tyreEntry.getKey(); // 轮胎标识，如 "t1"
            TyreTreadDepthVo.TyreData tyreData = new TyreTreadDepthVo.TyreData();

            // 获取并设置单个轮胎的判定结果
            tyreData.setTdResult(tyreEntry.getValue().get(DeviceDatasConstants.TD_RESULT).asText());

            Map<String, TyreTreadDepthVo.TyreData.GrooveData> grooveDataMap = new HashMap<>();
            // 遍历单个轮胎的每个凹槽
            tyreEntry.getValue().fields().forEachRemaining(grooveEntry -> {
                // 忽略TD_RESULT字段，设置每个凹槽的检测时间和检测值
                if (!grooveEntry.getKey().equals(DeviceDatasConstants.TD_RESULT)) {
                    TyreTreadDepthVo.TyreData.GrooveData grooveData = new TyreTreadDepthVo.TyreData.GrooveData();
                    grooveData.setTime(grooveEntry.getValue().get("time").asLong());
                    grooveData.setValue(grooveEntry.getValue().get("value").asDouble());
                    grooveDataMap.put(grooveEntry.getKey(), grooveData);
                }
            });

            tyreData.setGrooves(grooveDataMap);
            tyreDataMap.put(tyreKey, tyreData);
        });

        tyreTreadDepthVo.setData(tyreDataMap);

        return tyreTreadDepthVo;
    }
}

