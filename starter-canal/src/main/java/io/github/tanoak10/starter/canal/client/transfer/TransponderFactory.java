package io.github.tanoak10.starter.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import io.github.tanoak10.starter.canal.client.ListenerPoint;
import io.github.tanoak10.starter.canal.config.CanalConfig;
import io.github.tanoak10.starter.canal.event.CanalEventListener;

import java.util.List;
import java.util.Map;

/**
 * TransponderFactory
 *
 * @author chen.qian
 * @date 2018/3/23
 */
public interface TransponderFactory {

    /**
     * @param connector connector
     * @param config config
     * @param listeners listeners
     * @param annoListeners annoListeners
     * @return MessageTransponder
     */
    MessageTransponder newTransponder(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config, List<CanalEventListener> listeners,
                                      List<ListenerPoint> annoListeners);
}
