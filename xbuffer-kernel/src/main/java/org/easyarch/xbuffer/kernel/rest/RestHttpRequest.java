package org.easyarch.xbuffer.kernel.rest;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;

import java.util.*;

/**
 * Created by xingtianyu on 2018/11/1.
 */
public class RestHttpRequest {

    private String body;

    private Map<String,Object> pathVariable;
    private Map<String,Object> params;

    private Map<String,String> headers;

    private RestMethod method;

    public RestHttpRequest(FullHttpRequest request){
        init(request);
    }

    private void init(FullHttpRequest request){
        this.method = RestMethod.getMethod(request.method());
        initHeader(request.headers());
        initParams(new String(ByteBufUtil.getBytes(request.content())));
    }

    private void initParams(String body){
        this.body = body;
        this.params = JSON.parseObject(body);
        this.pathVariable = new HashMap<>();

    }

    private List<String> split(String url){
        if (url.startsWith("/")){
            url = url.substring(1);
        }
        String[] endpoints = url.split("/");
        List<String> asList = new ArrayList<>(Arrays.asList(endpoints));
        return asList;
    }

    private void initHeader(HttpHeaders headers){
        List<Map.Entry<String, String>> list = headers.entries();
        this.headers = new HashMap<>();
        for (Map.Entry<String, String> entry:list){
            this.headers.put(entry.getKey(),entry.getValue());
        }

    }

    public String pathVariable(String path){
        return String.valueOf(this.pathVariable.get(path));
    }

    public String body(){
        return body;
    }

    public<T> T body(Class<T> cls){
        return JSON.parseObject(body,cls);
    }

    public Map<String,Object> bodyAsMap(){
        return params;
    }

    public String header(String key){
        return headers.get(key);
    }

    public RestMethod method(){
        return method;
    }



}
