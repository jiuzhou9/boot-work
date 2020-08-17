package sskj.dev;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2020/07/29
 */
public class Json {

    public static void main(String[] args) {
        App app = new App();
        app.setPartNum("xxxxx");
        app.setPlanDate(LocalDate.of(2020, 7,30));
        app.setFactoryId(1);
        app.setQty(100);
        app.setCreateTime(LocalDateTime.now());
        app.setUpdateTime(LocalDateTime.now());
        System.out.println(JSON.toJSONString(app));

        DPS dps = new DPS();
        dps.setBatchId(123456789012345l);
        dps.setCreateTime(LocalDateTime.now());
        dps.setDpsId(1);
        dps.setFactoryId(1);
        dps.setLineNum("产线代码A");
        dps.setPartNum("xxxx");
        dps.setQty(100);
        dps.setShift("A");
        dps.setStartDate(LocalDate.now());
        dps.setUpdateTime(LocalDateTime.now());
        dps.setWorkHead("xxx");
        System.out.println(JSON.toJSONString(dps));
    }
}
@Data
class App{
    private String partNum;
    private int qty;
    private LocalDate planDate;
    private int factoryId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
@Data
class DPS {
    private int dpsId;
    private String workHead;
    private String partNum;
    private int qty;
    private String lineNum;
    private String shift;
    private LocalDate startDate;
    private long batchId;
    private int factoryId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}