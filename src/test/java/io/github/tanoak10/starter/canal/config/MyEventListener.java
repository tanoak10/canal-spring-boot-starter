package io.github.tanoak10.starter.canal.config;

import com.alibaba.otter.canal.protocol.CanalEntry;
import io.github.tanoak10.starter.canal.annotation.*;

/**
 * @author chen.qian
 * @date 2018/3/19
 */
@CanalEventListener
public class MyEventListener {

    @InsertListenPoint(destination = "example", schema = "canal-test", table = {"student"})
    public void onEvent(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        rowData.getAfterColumnsList().forEach((c) -> System.err.println("By--Annotation: " + c.getName() + " ::   " + c.getValue()));
    }

    @UpdateListenPoint
    public void onEvent1(CanalEntry.RowData rowData) {
        System.err.println("UpdateListenPoint");
        rowData.getAfterColumnsList().forEach((c) -> System.err.println("By--Annotation: " + c.getName() + " ::   " + c.getValue()));
    }

    @DeleteListenPoint
    public void onEvent3(CanalEntry.EventType eventType) {
        System.err.println("DeleteListenPoint");
    }

    @ListenPoint(destination = "example", schema = "canal-test", table = {"t_user", "test_table"}, eventType = CanalEntry.EventType.UPDATE)
    public void onEvent4(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.err.println("DeleteListenPoint");
        rowData.getAfterColumnsList().forEach((c) -> System.err.println("By--Annotation: " + c.getName() + " ::   " + c.getValue()));
    }

    @ListenPoint(schema = "westlake-smart-center", table = {"temp_test"}, eventType = {CanalEntry.EventType.INSERT,CanalEntry.EventType.UPDATE})
    public void insert(CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
        System.err.println("新增数据");
        rowData.getBeforeColumnsList().forEach((c) -> System.out.println("更改前 数据: " + c.getName() + " :: " + c.getValue()));
        rowData.getAfterColumnsList().forEach((c) -> System.out.println("更改后数 据: " + c.getName() + " :: " + c.getValue()));
    }

}
