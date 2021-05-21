package org.insa.graphs.algorithm.utils;

import org.insa.graphs.algorithm.shortestpath.AStarAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathAlgorithm;
import org.insa.graphs.algorithm.shortestpath.ShortestPathData;

public class AStarTest extends DijkstraTest  {


	protected static ShortestPathAlgorithm Algo(ShortestPathData data) {
    	return new AStarAlgorithm(data);
    }
    
}
