/**
 * Copyright (C) 2018-2023
 * All rights reserved, Designed By www.flma.tech
 * 注意：
 * 本软件为www.flma.tech开发研制，未经购买不得使用
 * 购买后可获得全部源代码（禁止转卖、分享、上传到码云、github等开源平台）
 * 一经发现盗用、分享等行为，将追究法律责任，后果自负
 */
package tech.flma.modules.devicedatas.strategy;

/**
 * 解析策略接口
 * @author flma
 * @date 2023-11-10
 */
public interface DataParsingStrategy {
    Object parse(String valueObj);
}

