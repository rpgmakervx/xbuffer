package org.easyarch.xbuffer.kernel.rest.router;

import com.google.common.collect.Maps;
import org.easyarch.xbuffer.kernel.rest.AbstractRestController;
import org.easyarch.xbuffer.kernel.rest.RestHttpRequest;
import org.easyarch.xbuffer.kernel.rest.RestHttpResponse;

import java.util.Map;

/**
 * Created by xingtianyu on 2018/11/3.
 * 字典树
 */
public class PathTrie<T> {

    private TrieNode root;

    private String separator;

    private String wildcard;

    public TrieNode getRoot() {
        return root;
    }

    public void setRoot(TrieNode root) {
        this.root = root;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getWildcard() {
        return wildcard;
    }

    public void setWildcard(String wildcard) {
        this.wildcard = wildcard;
    }

    public PathTrie(){
        this("/","*");
    }

    public PathTrie(String separator,String wildcard){
        this.separator = separator;
        this.wildcard = wildcard;
        this.root = new TrieNode(separator,null,wildcard);
    }

    public void insert(String key,T value){
        String[] strings = split(key);
        if (strings.length == 0){
            this.root.value = value;
            return;
        }
        this.root.insert(strings,0,value);
    }

    public T fetch(String key){
        return fetch(key,null);
    }

    public T fetch(String key,Map<String,String> params){
        String[] strings = split(key);
        if (strings.length == 0){
            return root.value;
        }
        return this.root.fetch(strings,0,params);
    }

    private String[] split(String key){
        String tmp = key;
        if (tmp.startsWith("/")){
            tmp = tmp.substring(1);
        }
        return tmp.split("/");
    }

    class TrieNode {

        private T value;

        private String key;

        private Map<String,TrieNode> children = Maps.newHashMap();

        private String pathVariableName;

        private boolean isWildCard;
        public TrieNode(String key,T value,String wildcard){
            this.key = key;
            this.value = value;
            this.isWildCard = key.equals(wildcard);
            if (isPathVariable(key)){
                this.pathVariableName = key.substring(key.indexOf('{') + 1, key.indexOf('}'));
            }
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Map<String, TrieNode> getChildren() {
            return children;
        }

        public void setChildren(Map<String, TrieNode> children) {
            this.children = children;
        }

        public String getPathVariableName() {
            return pathVariableName;
        }

        public void setPathVariableName(String pathVariableName) {
            this.pathVariableName = pathVariableName;
        }

        public boolean isWildCard() {
            return isWildCard;
        }

        public void setWildCard(boolean wildCard) {
            isWildCard = wildCard;
        }

        /**
         * POST /{index}/{type}/10001
         * @param path
         * @param index
         * @param value
         */
        public void insert(String[] path,int index,T value){
            if (index >= path.length){
                return;
            }
            String endpoint = path[index];
            String key = endpoint;
            if (isPathVariable(endpoint)){
                key = wildcard;
            }
            TrieNode node = children.get(key);
            //达到叶子节点
            if (node == null){
                node = new TrieNode(endpoint,index == path.length?null:value,wildcard);
                children.put(key,node);
                node.insert(path,index + 1,value);
            }
            node.insert(path,index + 1,value);
        }

        /**
         * GET /index/type/10001
         * @param path
         * @param index
         * @param params
         * @return
         */
        public T fetch(String[] path,int index,Map<String,String> params){
            if (index >= path.length){
                return null;
            }
            String endpoint = path[index];
            TrieNode node = children.get(endpoint);
            boolean isWildCard = false;
            //获取path失败，再获取通配符
            if (node == null){
                node = children.get(wildcard);
                if (node == null){
                    return null;
                }
                isWildCard = true;
            }
            //设置path variable
            if (isPathVariable(node.key)){
                if (params == null){
                    params = Maps.newHashMap();
                }
                params.put(node.pathVariableName,endpoint);
            }
            if (index == path.length - 1){
                return node.value;
            }
            return node.fetch(path,index + 1,params);
        }

        public boolean isPathVariable(String key){
            return key.indexOf("{") != -1 && key.indexOf("}") != -1;
        }

        @Override
        public String toString() {
            return "TrieNode{" +
                    "value=" + value +
                    ", key='" + key + '\'' +
                    ", children=" + children +
                    ", pathVariableName='" + pathVariableName + '\'' +
                    ", isWildCard=" + isWildCard +
                    '}';
        }
    }

    public static void main(String[] args) {
        PathTrie<AbstractRestController> trie = new PathTrie<>();
//        trie.insert("/index/type/{id}", new AbstractRestController() {
//            @Override
//            public void doAction(RestHttpRequest request, RestHttpResponse response) {
//                System.out.println("/index/type/{id}");
//            }
//        });
//        trie.insert("/index/type/_mapping", new AbstractRestController() {
//            @Override
//            public void doAction(RestHttpRequest request, RestHttpResponse response) {
//                System.out.println("/index/type/_mapping");
//            }
//        });
//        trie.insert("/hello/world", new AbstractRestController() {
//            @Override
//            public void doAction(RestHttpRequest request, RestHttpResponse response) {
//                System.out.println("hello world");
//            }
//        });
//        JSONObject json = new JSONObject();
//        System.out.println("trie:"+ JSONObject.toJSON(trie).toString());
//        System.out.println("trie:"+ trie);
        trie.fetch("/hello/world").doAction(null,null);
        trie.fetch("/index/type/_mapping").doAction(null,null);
        trie.fetch("/index/type/10001/ll").doAction(null,null);

    }

    @Override
    public String toString() {
        return "PathTrie{" +
                "root=" + root +
                ", separator='" + separator + '\'' +
                ", wildcard='" + wildcard + '\'' +
                '}';
    }
}
