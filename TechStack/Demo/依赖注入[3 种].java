/**
 * Copyright (C) 2018-2023
 * All rights reserved, Designed By www.flma.tech
 * 注意：
 * 本软件为www.flma.tech开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package tech.flma.modules.devicedatas.strategy.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tech.flma.modules.detect.record.domain.OrDetectRecord;
import tech.flma.modules.detect.record.service.mapper.OrDetectRecordMapper;
import tech.flma.modules.devicedatas.domain.OrDeviceDatas;
import tech.flma.modules.devicedatas.strategy.ParsingStrategy;
import tech.flma.modules.devicedatas.strategy.constant.DetectionIdConstants;
import tech.flma.modules.tools.utils.JacksonUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

/**
 * 轮胎花纹深度解析策略
 *
 * @author flma
 * @date 2023-11-21
 */

@Slf4j
@Component
public class TyreTreadDepthParsingStrategy implements ParsingStrategy {

    //  1. 字段注入
    @Autowired
    private OrDetectRecordMapper detectRecordMapper;

    //  2. setter注入
//    private static OrDetectRecordMapper detectRecordMapper;
//
//    @Autowired
//    public void setDetectRecordMapper(OrDetectRecordMapper detectRecordMapper) {
//        TyreTreadDepthParsingStrategy.detectRecordMapper = detectRecordMapper;
//    }

    //  3. 构造器注入 (或者使用@AllArgsConstructor)
//    private final OrDetectRecordMapper detectRecordMapper;
//
//    @Autowired
//    public TyreTreadDepthParsingStrategy(OrDetectRecordMapper detectRecordMapper) {
//        this.detectRecordMapper = detectRecordMapper;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean parse(OrDeviceDatas datas) {

        JsonNode jsonNode = (JsonNode) JacksonUtil.toNode(datas.getValueObj());

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                        jsonNode.path("tyreRecordDataList").elements(), Spliterator.ORDERED), false)
                .allMatch(tyreNode -> {
                    Map<String, Double> groovesMap = new HashMap<>(4);
                    tyreNode.path("groovesRecordDataList").forEach(grooveNode ->
                            groovesMap.put(grooveNode.path("grooveSeq").asText(), grooveNode.path("grooveDepth").asDouble())
                    );
                    // 提取检测值和检测结果
                    // 根据轮胎编号获取检测项ID
                    String deviceValue = JacksonUtil.toJson(groovesMap);
                    String detectValue = tyreNode.path("detectionResult").asText();
                    Long detectionId = getDetectionIdForTyre(tyreNode.path("tyreSeq").asText());
                    log.info("检测值: {}, 检测结果: {}, 检测项ID: {}", deviceValue, detectValue, detectionId);

                    // 更新数据库
                    OrDetectRecord detectRecord = OrDetectRecord.fillWithDetectResults(deviceValue, detectValue);

                    LambdaUpdateWrapper<OrDetectRecord> updateWrapper = new LambdaUpdateWrapper<>();

                    updateWrapper.eq(OrDetectRecord::getClerkId, datas.getBindUserId())
                            .eq(OrDetectRecord::getDeviceSn, datas.getDeviceSn())
                            .eq(OrDetectRecord::getDetectionId, detectionId)
                            .orderByDesc(OrDetectRecord::getCreateTime)
                            .last("LIMIT 1");

                    return detectRecordMapper.update(detectRecord, updateWrapper) > 0;
                });
    }

    /**
     * 获取轮胎编号对应的检测项ID
     *
     * @param tyreSeq 轮胎编号
     * @return 检测项ID
     */
    private Long getDetectionIdForTyre(String tyreSeq) {

        Map<String, Long> tyreToDetectionIdMap = new HashMap<>(4);
        tyreToDetectionIdMap.put("t1", DetectionIdConstants.DETECTION_ID_T1);
        tyreToDetectionIdMap.put("t2", DetectionIdConstants.DETECTION_ID_T2);
        tyreToDetectionIdMap.put("t3", DetectionIdConstants.DETECTION_ID_T3);
        tyreToDetectionIdMap.put("t4", DetectionIdConstants.DETECTION_ID_T4);

        return tyreToDetectionIdMap.get(tyreSeq);
    }

}
