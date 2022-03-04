package io.github.tanoak10.starter.canal.client;

import com.alibaba.otter.canal.client.CanalConnector;
import io.github.tanoak10.starter.canal.annotation.CanalEventListener;
import io.github.tanoak10.starter.canal.annotation.ListenPoint;
import io.github.tanoak10.starter.canal.client.transfer.TransponderFactory;
import io.github.tanoak10.starter.canal.config.CanalConfig;
import io.github.tanoak10.starter.canal.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * SimpleCanalClient
 * use cache thread pool to handle the event
 *
 * @author chen.qian
 * @since 2018/3/16
 */
public class SimpleCanalClient extends AbstractCanalClient {

    /**
     * executor
     */
    private final ThreadPoolExecutor executor;

    /**
     * listeners which are used by implementing the Interface
     */
    private final List<io.github.tanoak10.starter.canal.event.CanalEventListener> listeners = new ArrayList<>();

    /**
     * listeners which are used by annotation
     */
    private final List<ListenerPoint> annoListeners = new ArrayList<>();

    private static final Logger logger = LoggerFactory.getLogger(SimpleCanalClient.class);

    public SimpleCanalClient(CanalConfig canalConfig, TransponderFactory factory) {
        super(canalConfig, factory);
        executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<>(), Executors.defaultThreadFactory());
        initListeners();
    }

    @Override
    protected void process(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config) {
        executor.submit(factory.newTransponder(connector, config, listeners, annoListeners));
    }

    @Override
    public void stop() {
        super.stop();
        executor.shutdown();
    }

    /**
     * init listeners
     */
    private void initListeners() {
        logger.info("{}: initializing the listeners....", Thread.currentThread().getName());
        List<io.github.tanoak10.starter.canal.event.CanalEventListener> list = BeanUtil.getBeansOfType(io.github.tanoak10.starter.canal.event.CanalEventListener.class);
        if (list != null) {
            listeners.addAll(list);
        }
        Map<String, Object> listenerMap = BeanUtil.getBeansWithAnnotation(CanalEventListener.class);
        if (listenerMap != null) {
            for (Object target : listenerMap.values()) {
                Method[] methods = target.getClass().getDeclaredMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        ListenPoint l = AnnotationUtils.findAnnotation(method, ListenPoint.class);
                        if (l != null) {
                            annoListeners.add(new ListenerPoint(target, method, l));
                        }
                    }
                }
            }
        }
        logger.info("{}: initializing the listeners end.", Thread.currentThread().getName());
        if (logger.isWarnEnabled() && listeners.isEmpty() && annoListeners.isEmpty()) {
            logger.warn("{}: No listener found in context! ", Thread.currentThread().getName());
        }
    }
}
