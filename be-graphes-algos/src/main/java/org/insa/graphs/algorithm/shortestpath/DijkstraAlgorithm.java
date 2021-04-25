package org.insa.graphs.algorithm.shortestpath;

//import java.awt.List;
import java.util.ArrayList;

import org.insa.graphs.algorithm.utils.BinaryHeap;
//import org.insa.graphs.model.Arc;
import org.insa.graphs.model.*; //tous importer d'un coup
//import org.insa.graphs.model.Graph;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
	protected int NombreSommetsVisites;
	protected int Nombre_Sommets;

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.NombreSommetsVisites = 0;
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        Graph graph = data.getGraph(); // on récupère le graph de data
        /* On crée un tableau de Label, avec leur ID : l'indice est le sommet, et la case contient son Label */
        ArrayList<Label> labels = new ArrayList<Label>();
        /* On crée un tas de Label */
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        boolean dest = false; // pour savoir si on est arrivé à destination ou pas
        int Taille = graph.size(); //pour avoir la taille du graphe de data
        
        // TODO:
	
	/*On crée un tableau avec tout les prédecesseurs d'un sommet, on garde les arcs */
		Arc[] predecessor = new Arc[Taille];
		
	/* Initialisation */
		//On met tout les points à l'infini pour initialiser avec le constructeur
		for (Node noeud : graph.getNodes()) {
			labels.add(new Label(noeud));
		}
		//On ajoute le sommet de départ
		Label deb = labels.get(data.getOrigin().getId()); //origine de l'arc
		labels[deb.getSommet().getId()] = deb;
		deb.setInTas();
		deb.setCost(0);
		heap.insert(deb);
		
		/* Notifie les observateurs du premier évènement (départ de l'origine) */
		notifyOriginProcessed(data.getOrigin());
		
		/* Itérations: Tant qu'il existe des sommets non marqués */
		Label current;
		while(!heap.isEmpty() && !dest){
			current = heap.findMin();
			current = heap.deleteMin();
			heap.remove(current);
			/* On indique aux observateurs que le Node a été marqué */
			notifyNodeMarked(current.getSommet());
			current.setMark();
			
			/* On vérifie si on doit s'arrêter, arriver à la destination */
			if (current.getSommet() == data.getDestination()) {
				dest = true;
			}
			
			/* Parcours des successeurs du sommet courant */
			
			 for (Arc succ: current.getSommet().getSuccessors()) {
				 /* On recupere le label correspondant au noeud dans le tableau de labels */
				Label next= labels.get(succ.getDestination().getId());
				/* Si le successeur n'est pas encore marqué */
				if(next.getMark()==false) {
					/* Si on obtient un meilleur coût */
					/* Alors on le met à jour */
					if (next.getCost() > current.getCost() + succ.getLength()) {
						heap.remove(next);
						next.setCost(current.getCost() + succ.getLength());
						heap.insert(next);
						next.setFather(succ);
					}
			     }
			}
		}
		

        return solution;
    }

}
