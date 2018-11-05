package org.easyarch.xbuffer.kernel.mq.buffer;

import java.nio.ByteBuffer;

/**
 * Created by xingtianyu on 2018/10/26.
 * 实体包括以下四个特性：
 * 1.由字节流组成
 * 2.实体有长短，长短等于字节流长短。
 * 3.字节流必须是有意义的（根据业务场景），否则视实体为空。例如定义了头和体，缺少头只有体的实体可以视为空直接跳过
 */
public interface Entity<T extends Entity> {

    /**
     * 实体判空
     * @return
     */
    boolean isEmpty();

    /**
     *
     * 返回实体的字节缓冲区
     * @return
     */
    ByteBuffer content();

    int length();

    /**
     * 返回实体
     * 实现类可以自行处理返回结果，例如实体内容存在空则返回null等等
     * @return
     */
    T entity();

}
