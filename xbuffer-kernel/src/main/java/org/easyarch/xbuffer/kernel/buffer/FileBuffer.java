package org.easyarch.xbuffer.kernel.buffer;

import org.easyarch.xbuffer.kernel.XConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * Created by xingtianyu on 2018/10/21.
 */
public class FileBuffer extends AbstractBuffer {


    public void push(Event event) {
        try {
            File file = new File(XConfig.dataDir+"log-20181020"+XConfig.suffix);

            if (!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file,true);
            FileChannel channel = fos.getChannel();
            channel.write(event.getBody().contentBuffer());
            channel.force(true);
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    public void batch(List<Event> events) {

    }

    public Event pop() {
        return null;
    }

    public List<Event> drain(int batchSize) {
        return null;
    }

}
