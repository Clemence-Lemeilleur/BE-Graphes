package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label> {
	//On va faire un arbre de label afin de trouver directement le plus petit qui sera la racine
	public boolean Marked;
	public float Cost;
	public Node Father; //on préfèrera prendre l'arc pour retrouver le chemin à la fin : on ne servira pas de ça au final 
	public Node Sommet_Courant; 
	public boolean Tas; //vrai si notre noeud est dans le tas
	public Arc FatherArc;
	
	public float TotalCost;
	
//----------------------------Constructeur:-------------------------------------
	//Les noeuds des graphes sont numérotés de 0 à N-1
	public Label(Node noeud){
		this.Sommet_Courant = noeud;
		this.Marked = false;
		this.Cost = Float.POSITIVE_INFINITY;
		//this.Father = null; 
		this.Tas = false;
		this.FatherArc = null;
	}
	
//-----------------------------Méthodes: (pas utile comme mes attributs sont en public)----------------------------------------
	public Node getSommet() {
		return this.Sommet_Courant;
	}
	
	public float getCost() {
		return this.Cost;
	}
	
	public float getTotalCost() {
		return this.Cost;
	}
	
	
	/* Retourne true si le noeud a été marqué */
	public boolean getMark() {
		return this.Marked;
	}
	
	public Arc getFather() {
		return this.FatherArc;
	}
	
	/* Retourne true si le noeud a été mis dans le tas */
	public boolean getTas() {
		return this.Tas;
	}	
	
	public void setMark() {
		this.Marked = true;
	}
	
	public void setCost(float cout) {
		this.Cost = cout;
	}
	
	public void setFather(Arc fatherarc) {
		this.FatherArc = fatherarc;
	}
	
	public void setInTas() {
		this.Tas = true;
	}
	
//On est obligé de mettre une classe compareTo, puisque notre tas est implémenté d'une classe Compare
	
	/* On compare les coûts des Labels */
	public int compareTo(Label autre) {
		int compare;
		if (this.getTotalCost() < autre.getTotalCost()) {
			compare = -1;
		}
		else if (this.getTotalCost() == autre.getTotalCost()) {
			compare = 0;
		}
		else {
			compare = 1;
		}
		return compare;
		
	}
	
}


