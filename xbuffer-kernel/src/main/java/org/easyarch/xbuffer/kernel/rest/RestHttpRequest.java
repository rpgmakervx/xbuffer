package org.easyarch.xbuffer.kernel.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.*;

/**
 * Created by xingtianyu on 2018/11/1.
 */
public class RestHttpRequest {

    private String body;

    private Map<String,String> params = Maps.newHashMap();

    private Map<String,String> headers;

    private RestMethod method;

    private String url;

    public RestHttpRequest(FullHttpRequest request){
        init(request);
    }

    private void init(FullHttpRequest request){
        this.method = RestMethod.getMethod(request.method());
        this.body = new String(ByteBufUtil.getBytes(request.content()));
        this.url = request.uri();
        initHeader(request.headers());
        initParams(this.url);
    }

    private void initParams(String url){
        QueryStringDecoder decoder = new QueryStringDecoder(url);
        Map<String, List<String>> param = decoder.parameters();
        for(Map.Entry<String, List<String>> entry:param.entrySet()){
            params.put(entry.getKey(), entry.getValue().get(0));
        }
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

    public String body(){
        return body;
    }

    public<T> T body(Class<T> cls){
        return JSON.parseObject(body,cls);
    }

    public Map<String,Object> bodyAsMap(){
        return JSONObject.parseObject(body);
    }

    public String header(String key){
        return headers.get(key);
    }

    public RestMethod method(){
        return method;
    }

    public String param(String key){
        return this.params.get(key);
    }

    public Map<String,String> params(){
        return this.params;
    }

    public String url(){
        return this.url;
    }


}
