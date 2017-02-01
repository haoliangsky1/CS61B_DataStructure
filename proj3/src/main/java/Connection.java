import java.util.*;

/**
 * Created by haoliangsky on 4/1/16.
 */
public class Connection {
    private HashMap<Long, Node> graph;
    public HashMap<Long, Node> nodeSet;
    public HashMap<Long, ArrayList<Long>> wayMap;

    // for making that two nodes are connected
    public Connection(HashMap<Long, Node> nodes, HashMap<Long, ArrayList<Long>> ways) {
        // We have a set of nodes, iterate over the set to find connection with indicated by the wayMap
        graph = new HashMap<>();
        nodeSet = nodes;
        wayMap = ways;
        System.out.println(wayMap.get((long) 5149907));
//        for (long wayID : wayMap.keySet()) {
//            System.out.println(wayID);
//            System.out.println(wayMap.get(wayID));
//        }

        System.out.println("There are " + nodeSet.size() + " parsed nodes, 130462 expected.");
        System.out.println("There are " + wayMap.size() + " parsed highways");
        // Fill the graph
        Iterator it = nodes.entrySet().iterator();
        while (it.hasNext()) {
            // For each node in the node set, find all its connection and add as neighbors
            Map.Entry xPair = (Map.Entry) it.next();
            Node temp = connect((Node) xPair.getValue(), wayMap);
//            System.out.println("this node: " + temp.hasNeighbor());
            if (temp.hasNeighbor()) {
//                System.out.println("this node is connected.");
                graph.put(temp.id, temp);
            }
//            System.out.println("there are " + graph.size() + " connected graphs, 28654 expected");
        }
    }
    // Helper function that fill the neighbors according to the parsed XML
    public Node connect(Node node, HashMap<Long, ArrayList<Long>> wayMap) {
        // For this specific node, find all the connection it has in the wayMap
        long id = node.id;
//        System.out.println("the current node id: " + id);
//        Iterator way = wayMap.entrySet().iterator();
//        while (way.hasNext()) {
        for (long wayID : wayMap.keySet()) {
//            System.out.println(wayID);
            ArrayList<Long> listOfID = wayMap.get(wayID);
//            System.out.println(listOfID.size());
//            System.out.println(listOfID);
            if (listOfID.contains(id)) { // The way connects this node
//                System.out.println("yes, this way connects the current node");
                for (long x : listOfID) {
//                    System.out.println("The connected nodes are " + x);
                    Node neighbor = nodeSet.get(x);
                    node.addNeighbor(neighbor);
                }
            }
        }







//        for (Map.Entry<Long, ArrayList<Long>> pair : wayMap.entrySet()) {
////            System.out.println("look at the pair: " + pair.getValue());
//
////            System.out.println("connecting node " + id);
////            HashMap.Entry pair = (HashMap.Entry) way.next();
//            ArrayList<Long> listOfID = pair.getValue();
//            System.out.println("listOFID " + listOfID.size());
////            System.out.println("the neighbors");
////            System.out.println(pair.getValue());
//            if (listOfID.contains(id)) { // The way connects this node
//                System.out.println("yes, this way connects the current node");
//                for (long x : listOfID) {
//                    System.out.println("The connected nodes are " + x);
//                    Node neighbor = nodeSet.get(x);
//                    node.addNeighbor(neighbor);
//                }
//            }
//        }
        return node;
    }
    public HashMap<Long, Node> getGraph() {
        return this.graph;
    }

}
