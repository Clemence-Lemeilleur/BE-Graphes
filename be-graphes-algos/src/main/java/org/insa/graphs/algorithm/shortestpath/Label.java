package org.insa.graphs.algorithm.shortestpath;

public final class Label{
	//On va faire un arbre de label afin de trouver directement le plus petit qui sera la racine
	public int Sommet;
	public boolean Marque;
	public int Coût;
	public int Père; //on préfèrera prendre l'arc pour retrouver le chemin à la fin
	
	public void getCost() {
		
		return Coût;
		
	}
}


