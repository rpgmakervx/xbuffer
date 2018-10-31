package org.easyarch.xbuffer.kernel;

import org.easyarch.xbuffer.kernel.rest.router.RestRouteTable;

/**
 * Created by xingtianyu on 2018/11/1.
 * 全局信息保存在ClusterState中
 */
public class ClusterState {

    private static RestRouteTable restRoutTable = new RestRouteTable();

    public static RestRouteTable restRouteTable(){
        return restRoutTable;
    }

}
