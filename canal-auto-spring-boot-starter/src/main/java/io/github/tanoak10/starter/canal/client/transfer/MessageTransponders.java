package io.github.tanoak10.starter.canal.client.transfer;

/**
 * @author chen.qian
 * @since 2018/3/23
 */
public class MessageTransponders {

    public static TransponderFactory defaultMessageTransponder() {
        return new DefaultTransponderFactory();
    }

}
