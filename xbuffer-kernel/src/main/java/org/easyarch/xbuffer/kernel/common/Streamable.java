package org.easyarch.xbuffer.kernel.common;

import org.easyarch.xbuffer.kernel.common.io.StreamInput;
import org.easyarch.xbuffer.kernel.common.io.StreamOutput;

import java.io.IOException;

/**
 * Created by xingtianyu on 2018/10/24.
 */
public interface Streamable {

    void readFrom(StreamInput in) throws IOException;

    void writeTo(StreamOutput out) throws IOException;
}
