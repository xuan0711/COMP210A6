package a6;

import java.util.HashMap;

public class NodeImpl implements Node {

    /* You will include the method signatures (return type, name, and arg types) for any node methods you
    need in this file. */

    /*Hint: Make sure you update the Node interface in Node.java when you add a new method implementation
    in NodeImpl.java, and vice-versa.  getName() in Node.java and NodeImpl.java is an example.  Also, files in
    previous homeworks (e.g., BST.java and BSTImpl.java in homework 3) are good examples of
    interfaces and their implementations.
     */

    /*Also, any node fields you want to add for the object should go in this file.  */
    private String name;
    private HashMap<String, Edge> inEdges;
    private HashMap<String, Edge> outEdges;
    private Node pn;
    private boolean known;
    private double dv;

    public NodeImpl(String name) {
        this.name = name;
        this.inEdges = new HashMap();
        this.outEdges = new HashMap();
        pn = null;
        known = false;
        dv = Double.MAX_VALUE;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public HashMap<String, Edge> getInEdges() {
        return this.inEdges;
    }

    public HashMap<String, Edge> getOutEdges() {
        return this.outEdges;
    }

    public void addInEdge(Edge inEdge) {
        inEdges.put(inEdge.getSrc(), inEdge);
    }

    public void removeInEdge(Edge inEdge) {
        inEdges.remove(inEdge.getSrc(), inEdge);
    }

    public void addOutEdge(Edge outEdge) {
        outEdges.put(outEdge.getDest(), outEdge);
    }

    public void removeOutEdge(Edge outEdge) {
        outEdges.remove(outEdge.getDest(), outEdge);
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public double getDv() {
        return dv;
    }

    public void setDv(double dv) {
        this.dv = dv;
    }

    public Node getPn() {
        return pn;
    }

    public void setPn(Node pn) {
        this.pn = pn;
    }

    public String toString() {
        return "Name: " + name + "\tdv = " + dv + "\t" + known;
    }

    public int compareTo(Node n) {
        return (int) (this.dv - n.getDv());
    }
}