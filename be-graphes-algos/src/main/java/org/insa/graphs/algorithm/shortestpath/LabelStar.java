package org.insa.graphs.algorithm.shortestpath;

//import org.insa.graphs.algorithm.shortestpath.ShortestPathData;
import org.insa.graphs.model.Node;
//import org.insa.graphs.model.Point;
//import org.insa.graphs.algorithm.AbstractInputData;

public class LabelStar extends Label{
	private float cout_estime;
	private float cout_total;

	public LabelStar(Node sommet, ShortestPathData data) {
		super(sommet);
		cout_estime = 0;
		cout_total=cout_estime + this.getCost();
		//this.val_estime = val_estime;
	}
	
	/* Renvoie le coût de l'origine jusqu'au noeud + coût à vol d'oiseau du noeud jusqu'à la destination: ESTIMATION */
	public float GetHeuristicCost() {
		this.cout_total = this.getCost() + this.cout_estime;
		//System.out.println(" cout: " + this.getCost() + " cout estimé: " + this.getEstimation());
		return this.cout_total;
	}
	
	//Renvoie une estimation du cout depuis l'origine
	public float getEstimation() {
		return this.cout_estime;
		
	}
	
	//Calcule une estimation du cout depuis l'origine
	public void setEstimation(float cout) {
		this.cout_estime = cout;
		
	}
	
	/* On compare les coûts des Labels */
	public int compareTo(Label autre) {
		int compare;
		//on caste le Label en LabelStar pour avoir accès à l'estimation du cout jusqu'à la destination en plus du coût du chemin déjà parcouru 
		if (this.GetHeuristicCost() < ((LabelStar) autre).GetHeuristicCost()) {
			compare = -1;
		}
		else if (this.GetHeuristicCost() == ((LabelStar) autre).GetHeuristicCost()) {
			compare = 0;
		}
		else {
			compare = 1;
		}
		return compare;
		
	}
	
	

}

//Le compare tout n'est pas modifie, on y fait appel ici