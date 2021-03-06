package org.insa.graphs.algorithm.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.algorithm.shortestpath.ShortestPathSolution;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.Point;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.junit.BeforeClass;
import org.junit.Test;

import org.insa.graphs.algorithm.shortestpath.DijkstraAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.BellmanFordAlgorithm;

public class DijkstraTest {

    // Small graph use for tests
    private static Graph graph, graph_toulouse;
    
    private static String map_toulouse = "/Users/clemencelemeilleur/Desktop/STPI 3/S2/BE Graphes/MAPS/toulouse.mapgr";

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;
    
    // Data inputed in Dijkstra
    private static ShortestPathData shortData, invalidData, 
    								toulouseD1, toulouseD2;
    
    // Result of the algorithm
    private static Path shortPath, toulouseP1, toulouseP2,
    					shortSol, toulouseS1, toulouseS2;
    
   //Invalid Solution
    private static ShortestPathSolution invalidSolution;

    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds: On d??fini les routes
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                		speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, "");

        // Create nodes for small graphs
        nodes = new Node[6];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, new Point(ThreadLocalRandom.current().nextFloat()*10,ThreadLocalRandom.current().nextFloat()*10));
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);
        
        // Construct the Graphs
        BinaryGraphReader toulouseReader = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map_toulouse))));
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        graph_toulouse = toulouseReader.read();
        
        // ---- Construct Data
        shortData = new ShortestPathData(graph, nodes[0], nodes[3], ArcInspectorFactory.getAllFilters().get(0));
        invalidData = new ShortestPathData(graph, nodes[0], nodes[5], ArcInspectorFactory.getAllFilters().get(0));
        
        // ---- Construct Toulouse Data
        // Domicile -> INSA - Voiture
        toulouseD1 = new ShortestPathData(graph_toulouse, graph_toulouse.getNodes().get(23035), graph_toulouse.getNodes().get(20004), ArcInspectorFactory.getAllFilters().get(0));
        // INSA -> CNES - Pied
        toulouseD2 = new ShortestPathData(graph_toulouse, graph_toulouse.getNodes().get(8206), graph_toulouse.getNodes().get(7463), ArcInspectorFactory.getAllFilters().get(0));
    
        // Get path with dijkstra
        shortPath =  Algo(shortData).run().getPath();
        toulouseP1 = Algo(toulouseD1).run().getPath();
        toulouseP2 = Algo(toulouseD2).run().getPath();
        
        // Get path with BF
        shortSol = new BellmanFordAlgorithm(shortData).run().getPath();
        toulouseS1 = new BellmanFordAlgorithm(toulouseD1).run().getPath();
        toulouseS2 = new BellmanFordAlgorithm(toulouseD2).run().getPath();
        
        //Solution invalide (graphe pas connexe)
        invalidSolution = Algo(invalidData).run();
    }

    @Test
    public void testIsEmpty() {
        assertFalse(shortPath.isEmpty());
        assertFalse(toulouseP1.isEmpty());
        assertFalse(toulouseP2.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals(shortSol.size(), shortPath.size());
        assertEquals(toulouseS1.size(), toulouseP1.size());
        assertEquals(toulouseS2.size(), toulouseP2.size());
    }

    @Test
    public void testIsValid() {
        assertTrue(shortPath.isValid());
        assertTrue(toulouseP1.isValid());
        assertTrue(toulouseP2.isValid());
        assertNull(invalidSolution.getPath());
    }

    @Test
    public void testGetLength() {
        assertEquals(shortSol.getLength(), shortPath.getLength(), 1e-6);
        assertEquals(toulouseS1.getLength(), toulouseP1.getLength(), 1e-6);
        assertEquals(toulouseS2.getLength(), toulouseS2.getLength(), 1e-6);
    }
    
    @Test
    public void testExist() {
        assertEquals(invalidSolution.getStatus(), Status.INFEASIBLE);
    }

    @Test
    public void testGetTravelTime() {
        // Note: 18 km/h = 5m/s
        assertEquals(shortSol.getTravelTime(18), shortPath.getTravelTime(18), 1e-6); // 1e-6 est la pr??cision, l'erreur acceptable
        assertEquals(toulouseS1.getTravelTime(18), toulouseP1.getTravelTime(18), 1e-6);
        assertEquals(toulouseS2.getTravelTime(18), toulouseP2.getTravelTime(18), 1e-6);

        // Note: 28.8 km/h = 8m/s
        assertEquals(shortSol.getTravelTime(28.8), shortPath.getTravelTime(28.8), 1e-6);
        assertEquals(toulouseS1.getTravelTime(28.8), toulouseP1.getTravelTime(28.8), 1e-6);
        assertEquals(toulouseS2.getTravelTime(28.8), toulouseP2.getTravelTime(28.8), 1e-6);
    }

    protected static ShortestPathAlgorithm Algo(ShortestPathData data) {
    	return new DijkstraAlgorithm(data);
    }
//Une autre m??thode aurait ??t?? de g??n??rer plusieurs tests al??atoires, sans tracer un chemin nous m??me pour v??rifier que tout marche bien: on cr??e al??atoirement un noeud de d??part et de destination entre la taille des coordon??es possible et on v??rifie que le chemin est valide...
}