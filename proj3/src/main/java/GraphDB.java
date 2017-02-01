import com.sun.org.apache.xml.internal.utils.Trie;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.*;

/**
 * Wraps the parsing functionality of the MapDBHandler as an example.
 * You may choose to add to the functionality of this class if you wish.
 * @author Alan Yao
 */
public class GraphDB {
    /**
     * Example constructor shows how to create and start an XML parser.
     * @param db_path Path to the XML file to be parsed.
     */
    public HashMap<Long, Node> graph;
    public TrieST trieTree;

    public GraphDB(String db_path) {
        graph = new HashMap<>();
        try {
            File inputFile = new File(db_path);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            MapDBHandler maphandler = new MapDBHandler(this);
            saxParser.parse(inputFile, maphandler);
            // Get a TrieST of the nodes
            TrieST trieTree = new TrieST(maphandler.getNodeSet());



            // Use these two sets of info to construct a graph.
//            System.out.println(maphandler.getNodeSet().get((long) 286080658).getLat());
            // We can do this in the connection class;
//            System.out.println(maphandler.getWayMap().size());

//            System.out.println("import size: " + maphandler.getWayMap().size());
//            System.out.println(maphandler.getWayMap().get((long) 5149907));
//            Connection connect = new Connection(maphandler.getNodeSet(), maphandler.getWayMap());
//            // Then get a graph
//            graph = connect.getGraph();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        clean();
    }

    public TrieST getTrieTree() {
        return this.trieTree;
    }

    /**
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     *  Remove nodes with no connections from the graph.
     *  While this does not guarantee that any two nodes in the remaining graph are connected,
     *  we can reasonably assume this since typically roads are connected.
     */
    private void clean() {
    }
}
