package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.utils.BinaryHeap;
//import org.insa.graphs.model.Arc;
import org.insa.graphs.model.*; //tous importer d'un coup
//import org.insa.graphs.model.Graph;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        boolean dest = false; // pour savoir si on est arrivé à destination ou pas
        Graph graph = data.getGraph(); // on récupère le graph de data
        int Taille = graph.size(); //pour avoir la taille du graphe de data
        
        // TODO:
   /* On crée un tableau de Label, avec leur ID : l'indice est le sommet, et la case contient son Label */
        Label tabLab[] = new Label [Taille];
   
   /* On crée un tas de Label */
		BinaryHeap<Label> tas = new BinaryHeap<Label>();
	
	/*On crée un tableau avec tout les prédecesseurs d'un sommet, on garde les arcs */
		Arc[] predecessor = new Arc[Taille];
		
	/* Initialisation */
		//On met tout les points à l'infini pour initialiser avec le constructeur
		for (Node noeud : graph.getNodes()) {
			tabLab[noeud.getId()] = new Label(noeud);
		}
		//On ajoute le sommet de départ
		Label deb = new Label(data.getOrigin());
		tabLab[deb.getSommet().getId()] = deb;
		tas.insert(deb);
		deb.setInTas();
		deb.setCost(0);
		
		

        return solution;
    }

}
