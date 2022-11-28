package a6;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;

public class GraphImpl implements Graph {
    Map<String, Node> nodes; //Do not delete.  Use this field to store your nodes.
    // key: name of node. value: a5.Node object associated with name

    HashSet<Edge> edges;

    public GraphImpl() {
        nodes = new HashMap<>();
        edges = new HashSet<>();
    }

    @Override
    public boolean addNode(String name) {
        if (nodes.containsKey(name) || name == null) {
            return false;
        }
        nodes.put(name, new NodeImpl(name));
        return true;
    }

    @Override
    public boolean addEdge(String src, String dest, double weight) {
        if (weight < 0 || ! nodes.containsKey(src) || ! nodes.containsKey(dest)){
            return false;
        }
        if (nodes.containsKey(src) && nodes.containsKey(dest) && nodes.get(src).getOutEdges().containsKey(dest)){
            return false;
        }
        Edge e = new EdgeImpl(src, dest, weight);
        nodes.get(src).addOutEdge(e);
        nodes.get(dest).addInEdge(e);
        edges.add(e);
        return true;
    }

    @Override
    public boolean deleteNode(String name) {
        if (!nodes.containsKey(name)) {
            return false;
        }
        HashMap<String, Edge> relatives = nodes.get(name).getOutEdges();
        for (Map.Entry<String, Edge> entry : relatives.entrySet()) {
            Edge e = entry.getValue();
            deleteEdge(e.getSrc(), e.getDest());
        }
        relatives = nodes.get(name).getInEdges();
        for (Map.Entry<String, Edge> entry : relatives.entrySet()) {
            Edge value = entry.getValue();
            deleteEdge(value.getSrc(), value.getDest());
        }
        nodes.remove(name, nodes.get(name));
        return true;
    }

    @Override
    public boolean deleteEdge(String src, String dest) {
        if (!(nodes.containsKey(src) && nodes.get(src).getOutEdges().containsKey(dest))) {
            return false;
        }
        Edge e = nodes.get(src).getOutEdges().get(dest);
        nodes.get(src).removeOutEdge(e);
        nodes.get(dest).removeInEdge(e);
        edges.remove(e);
        return true;
    }

    @Override
    public int numNodes() {
        return nodes.size();  //Dummy return value.  Remove when you implement!
    }

    @Override
    public int numEdges() {
        return edges.size();  //Dummy return value.  Remove when you implement!
    }

    @Override

    public Map<String, Double> dijkstra(String start) {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        Map<String, Double> map = new HashMap<>();
        Node startNode = nodes.get(start);
        startNode.setPn(null);
        startNode.setDv(0);
        pq.add(startNode);
        while (!pq.isEmpty()) {
            Node n = pq.poll();
            double d = n.getDv();
            if (n.isKnown()) {
                continue;
            }
            n.setKnown(true);
            HashMap<String, Edge> selects = n.getOutEdges();
            for (Map.Entry<String, Edge> entry : selects.entrySet()) {
                Node a = nodes.get(entry.getKey());
                Edge edge = entry.getValue();
                if (!a.isKnown()) {
                    if (a.getDv() > d + edge.getWeight()) {
                        a.setDv(d + edge.getWeight());
                        a.setPn(n);
                        pq.add(a);
                    }
                }
            }
        }
        // fill map
        for (String key : nodes.keySet()) {
            if (nodes.get(key).isKnown()) {
                map.put(key, nodes.get(key).getDv());
            }
            //System.out.println(key + ": " + map.get(key));
        }
        return map;
    }
}

