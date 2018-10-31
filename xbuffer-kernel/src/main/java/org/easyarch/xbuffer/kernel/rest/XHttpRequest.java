package org.easyarch.xbuffer.kernel.rest;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xingtianyu on 2018/11/1.
 */
public class XHttpRequest {

    private String body;

    private Map<String,String> headers;

    private HttpMethod method;

    public XHttpRequest(FullHttpRequest request){
        init(request);
    }

    private void init(FullHttpRequest request){
        byte[] data = ByteBufUtil.getBytes(request.content());
        this.body = new String(data);
        this.method = request.method();
        List<Map.Entry<String, String>> list = request.headers().entries();
        this.headers = new HashMap<>();
        for (Map.Entry<String, String> entry:list){
            this.headers.put(entry.getKey(),entry.getValue());
        }
    }

    public String body(){
        return body;
    }

    public<T> T body(Class<T> cls){
        return JSON.parseObject(body,cls);
    }

    public Map<String,Object> bodyAsMap(){
        return JSON.parseObject(body);
    }

    public String header(String key){
        return headers.get(key);
    }

    public HttpMethod method(){
        return method;
    }



}
