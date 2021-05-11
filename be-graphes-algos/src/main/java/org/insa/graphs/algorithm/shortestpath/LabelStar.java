package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;
import org.insa.graphs.algorithm.AbstractInputData;

public class LabelStar extends Label{
	private float val_estime;

	public LabelStar(Node noeud, ShortestPathData data) {
		super(noeud);
		this.val_estime = val_estime;
	}

	@Override
	/* Renvoie le coût de l'origine jusqu'au noeud + coût à vol d'oiseau du noeud jusqu'à la destination */
	public float getTotalCost() {
		return this.val_estime+this.Cost;
	}

}

//Le compare tout n'est pas modifie, on y fait appel ici