package org.easyarch.xbuffer.kernel.rest.router;

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

    private PathTrie<AbstractRestController> getTrie = new PathTrie();
    private PathTrie<AbstractRestController> postTrie = new PathTrie();
    private PathTrie<AbstractRestController> putTrie = new PathTrie();
    private PathTrie<AbstractRestController> deleteTrie = new PathTrie();

    public AbstractRestController getController(RestHttpRequest request){
        PathTrie<AbstractRestController> trie = getTable(request.method());
        AbstractRestController controller = trie.fetch(request.url(),request.params());
        if (controller == null){
            return new NotFoundController();
        }
        return controller;
    }

    public void registController(RestMethod method,String url,AbstractRestController controller){
        PathTrie trie = getTable(method);
        trie.insert(url,controller);
    }

    private List<String> split(String url){
        if (url.startsWith("/")){
            url = url.substring(1);
        }
        String[] endpoints = url.split("/");
        List<String> asList = new ArrayList<>(Arrays.asList(endpoints));
        return asList;
    }

    private PathTrie getTable(RestMethod method){
        switch (method){
            case GET:
                return getTrie;
            case POST:
                return postTrie;
            case PUT:
                return putTrie;
            case DELETE:
                return deleteTrie;
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
//        table.getController(RestMethod.GET,"/_plugin/xingtianyu/r").doAction(null,null);
    }

}
