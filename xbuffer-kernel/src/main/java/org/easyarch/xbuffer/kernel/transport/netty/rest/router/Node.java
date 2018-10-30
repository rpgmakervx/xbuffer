package org.easyarch.xbuffer.kernel.transport.netty.rest.router;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xingtianyu(code4j) Created on 2018-10-30.
 */
public class Node {

    private Router router;

    private List<Node> children = new ArrayList<>();

    public Node child(String endpoint){
        for (Node child:children){
            Router router = child.router;
            if (router.hint(endpoint)){
                return child;
            }
        }
        return null;
    }

    public void addChild(Node node){
        this.children.add(node);
    }

    public boolean isLeaf(){
        return !router.isEmpty();
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Node{" +
                "router=" + router +
                ", children=" + children +
                '}';
    }
}
