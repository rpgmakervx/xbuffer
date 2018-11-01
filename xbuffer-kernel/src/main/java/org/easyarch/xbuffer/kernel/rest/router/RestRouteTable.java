package org.easyarch.xbuffer.kernel.rest.router;

import com.alibaba.fastjson.JSON;
import io.netty.handler.codec.http.HttpMethod;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;
import org.easyarch.xbuffer.kernel.rest.RestMethod;
import org.easyarch.xbuffer.kernel.rest.controller.NotFoundController;

import java.util.*;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 * rest请求路由表
 */
public class RestRouteTable {

    private Map<String,Node> getTable = new HashMap<>();
    private Map<String,Node> postTable = new HashMap<>();
    private Map<String,Node> putTable = new HashMap<>();
    private Map<String,Node> deleteTable = new HashMap<>();

    public AbstractRestController getController(RestMethod method, String url){
        List<String> endpoints = split(url);
        String rootEndPoint = endpoints.get(0);
        Map<String,Node> table = getTable(method);
        Node root = table.get(rootEndPoint);
        endpoints.remove(0);
        for (String ep:endpoints){
            root = root.child(ep);
            if (root == null){
                return new NotFoundController();
            }
            if (root.isLeaf()){
                return root.getRouter().getController();
            }
        }
        return null;
    }

    public void registController(RestMethod method,String url,AbstractRestController controller){
        List<String> endpoints = split(url);
        String rootEndPoint = endpoints.get(0);
        Map<String,Node> table = getTable(method);
        Node root = table.get(rootEndPoint);
        if (root == null) {
            root = new Node();
        }
        if (endpoints.size() == 0){
            Router router = new Router(rootEndPoint,controller);
            root.setRouter(router);
            table.put(rootEndPoint,root);
            return;
        }
        Router router = new Router(rootEndPoint,null);
        root.setRouter(router);
        table.put(rootEndPoint,root);

        endpoints.remove(0);
        int index = 0;
        for (String endPoint:endpoints){
            Node node = new Node();
            Router rt = new Router(endPoint);
            if (index == endpoints.size() - 1){
                rt.setController(controller);
            }
            node.setRouter(rt);
            root.addChild(node);
            root = node;
            index++;
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

    private Map<String,Node> getTable(RestMethod method){
        switch (method){
            case GET:
                return getTable;
            case POST:
                return postTable;
            case PUT:
                return putTable;
            case DELETE:
                return deleteTable;
        }
        return null;
    }

    public static void main(String[] args) {
        RestRouteTable table = new RestRouteTable();
        table.registController(RestMethod.GET,"/_plugin/kopf", new AbstractRestController() {
            @Override
            public void doAction(RestHttpRequest request, RestHttpResponse response) {
                System.out.println("/_plugin/kopf");
            }
        });
        table.registController(RestMethod.GET,"/_plugin/head", new AbstractRestController() {
            @Override
            public void doAction(RestHttpRequest request, RestHttpResponse response) {
                System.out.println("/_plugin/head");

            }
        });
        table.registController(RestMethod.GET,"/_plugin/hq", new AbstractRestController() {
            @Override
            public void doAction(RestHttpRequest request, RestHttpResponse response) {
                System.out.println("/_plugin/hq");
            }
        });
        table.registController(RestMethod.GET,"/_plugin/{name}/run", new AbstractRestController() {
            @Override
            public void doAction(RestHttpRequest request, RestHttpResponse response) {
                System.out.println("/_plugin/{name}/run");
            }
        });
        table.registController(RestMethod.POST,"/_cluster/health", new AbstractRestController() {
            @Override
            public void doAction(RestHttpRequest request, RestHttpResponse response) {
                System.out.println("/_cluster/health");
            }
        });
        table.getController(RestMethod.GET,"/_plugin/xingtianyu/r").doAction(null,null);
    }

}
