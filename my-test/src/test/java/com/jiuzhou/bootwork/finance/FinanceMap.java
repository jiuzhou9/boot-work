package com.jiuzhou.bootwork.finance;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/10/14
 */
public class FinanceMap {

    private Map<LocalDate, Finance> map;

    public FinanceMap(Map<LocalDate, Finance> map) {
        this.map = map;
    }
}
