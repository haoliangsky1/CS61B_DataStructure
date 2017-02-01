import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.lang.reflect.Array;
import java.util.*;

/**
 *  Parses OSM XML files using an XML SAX parser. Used to construct the graph of roads for
 *  pathfinding, under some constraints.
 *  See OSM documentation on
 *  <a href="http://wiki.openstreetmap.org/wiki/Key:highway">the highway tag</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Way">the way XML element</a>,
 *  <a href="http://wiki.openstreetmap.org/wiki/Node">the node XML element</a>,
 *  and the java
 *  <a href="https://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html">SAX parser tutorial</a>.
 *  @author Alan Yao
 */
public class MapDBHandler extends DefaultHandler {
    /**
     * Only allow for non-service roads; this prevents going on pedestrian streets as much as
     * possible. Note that in Berkeley, many of the campus roads are tagged as motor vehicle
     * roads, but in practice we walk all over them with such impunity that we forget cars can
     * actually drive on them.
     */
    private static final Set<String> ALLOWED_HIGHWAY_TYPES = new HashSet<>(Arrays.asList
            ("motorway", "trunk", "primary", "secondary", "tertiary", "unclassified",
                    "residential", "living_street", "motorway_link", "trunk_link", "primary_link",
                    "secondary_link", "tertiary_link"));
    public String activeState = "";
    private final GraphDB g;
    public HashMap<Long, Node> nodeSet;
    public HashMap<Long, ArrayList<Long>> wayMap;
    public ArrayList<Long> refList;
    public long nodeID = 0;
    public long wayID = 0;
    public boolean wayFlag = false;
    public boolean hasName = false;
    public String name;

    public MapDBHandler(GraphDB g) {
        this.g = g;
        nodeSet = new HashMap<>();
        wayMap = new HashMap<>();
        refList = new ArrayList<>();
        wayFlag = false;
        System.out.println("new handler");
        nodeID = 0;
        wayID = 0;
        name = null;
    }
    public HashMap<Long, Node> getNodeSet() {
        return this.nodeSet;
    }
    public HashMap<Long, ArrayList<Long>> getWayMap() {
        return this.wayMap;
    }

    /**
     * Called at the beginning of an element. Typically, you will want to handle each element in
     * here, and you may want to track the parent element.
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or
     *            if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are
     *              not available. This tells us which element we're looking at.
     * @param attributes The attributes attached to the element. If there are no attributes, it
     *                   shall be an empty Attributes object.
     * @throws SAXException Any SAX exception, possibly wrapping another exception.
     * @see Attributes
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
            throws SAXException {
        /* Some example code on how you might begin to parse XML files. */
        if (qName.equals("relation")) {
            return;
        }
        long ref;
        double lat = 0;
        double lon = 0;
        if (qName.equals("node")) {
            activeState = "node";
            // Begin parsing here:
            nodeID = Long.parseLong(attributes.getValue("id"));
            lat = Double.parseDouble(attributes.getValue("lat"));
            lon = Double.parseDouble(attributes.getValue("lon"));
            name = null;
        } else if (qName.equals("way")) {
            activeState = "way";
            refList = new ArrayList<>();
            // Pull out a list of the reference ID that the road connects to, if the type is one of the highway types;
        } else if (activeState.equals("way") && qName.equals("nd")) { // We need these input as connection
            ref = Long.parseLong(attributes.getValue("ref"));
            refList.add(ref);
        } else if (activeState.equals("way") && qName.equals("tag") && attributes.getValue("k")
                .equals("highway")) {
            String v = attributes.getValue("v");

        } else if (activeState.equals("node") && qName.equals("tag") && attributes.getValue("k")
                .equals("name")) {
            // There maybe more info for one node
            name = attributes.getValue("v");
            hasName = true;
        }
        if (qName.equals("node")) {
            Node node = new Node(nodeID, lat, lon, name);
            nodeSet.put(node.id, node);
        }
    }

    /**
     * Receive notification of the end of an element. You may want to take specific terminating
     * actions here, like finalizing vertices or edges found.
     * @param uri The Namespace URI, or the empty string if the element has no Namespace URI or
     *            if Namespace processing is not being performed.
     * @param localName The local name (without prefix), or the empty string if Namespace
     *                  processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are
     *              not available.
     * @throws SAXException  Any SAX exception, possibly wrapping another exception.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("relation")) {
            return;
        }
        if (qName.equals("node")) {
            if (hasName) {
                nodeSet.get(nodeID).name = name;
                hasName = false;
            }
        } else if (qName.equals("way")) {
            //
        }
    }
    // Supposedly, after these operation the two set should be ready
    // The nodeSet should have all the nodes
    // The wayMap should have all navigable ways

}
