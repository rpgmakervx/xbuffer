package org.easyarch.xbuffer.kernel.rest;

import io.netty.handler.codec.http.HttpMethod;

/**
 * @author xingtianyu(code4j) Created on 2018-11-1.
 */
public enum RestMethod {

    GET,POST,PUT,DELETE;

    public static RestMethod getMethod(HttpMethod method){
        if (HttpMethod.GET.equals(method)){
            return GET;
        }else if (HttpMethod.POST.equals(method)){
            return POST;
        }else if (HttpMethod.PUT.equals(method)){
            return PUT;
        }else if (HttpMethod.DELETE.equals(method)){
            return DELETE;
        }
        return GET;
    }

}
