package org.easyarch.xbuffer.kernel.transport.netty.rest.router;

import com.alibaba.fastjson.JSON;
import org.easyarch.xbuffer.kernel.transport.netty.rest.AbstractRestController;

import java.util.*;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public class RouteTable {

    public Map<String,Node> table = new HashMap<>();

    public AbstractRestController getController(String url){
        List<String> endpoints = split(url);
        String rootEndPoint = endpoints.get(0);
        Node root = table.get(rootEndPoint);
        endpoints.remove(0);
        for (String ep:endpoints){
            root = root.child(ep);
            if (root.isLeaf()){
                return root.getRouter().getController();
            }
        }
        return null;
    }

    public void registController(String url,AbstractRestController controller){
        List<String> endpoints = split(url);
        String rootEndPoint = endpoints.get(0);
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

    public static void main(String[] args) {
        RouteTable table = new RouteTable();
        table.registController("/_plugin/kopf", new AbstractRestController() {
            @Override
            public void doAction() {
                System.out.println("/_plugin/kopf");
            }
        });
        table.registController("/_plugin/head", new AbstractRestController() {
            @Override
            public void doAction() {
                System.out.println("/_plugin/head");

            }
        });
        table.registController("/_plugin/hq", new AbstractRestController() {
            @Override
            public void doAction() {
                System.out.println("/_plugin/hq");
            }
        });
        table.registController("/_plugin/{name}/run", new AbstractRestController() {
            @Override
            public void doAction() {
                System.out.println("/_plugin/{name}/run");
            }
        });
        table.registController("/_cluster/health", new AbstractRestController() {
            @Override
            public void doAction() {
                System.out.println("/_cluster/health");
            }
        });
        System.out.println(JSON.toJSONString(table.table));
        table.getController("/_plugin/me/run").doAction();
    }

}
