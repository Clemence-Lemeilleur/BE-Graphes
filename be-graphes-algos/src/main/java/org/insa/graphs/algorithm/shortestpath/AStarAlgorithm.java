package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.shortestpath.Label;
import org.insa.graphs.algorithm.shortestpath.LabelStar;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

	public AStarAlgorithm(ShortestPathData data) {
		super(data);
	}
	
	@Override
	/* Réécriture de la méthode newLabel */
	/* afin d'utiliser LabelStar au lieu de Label dans l'algo */
	protected Label newLabel(Node node, ShortestPathData data) {
		return new LabelStar(node, data);
	}

}
