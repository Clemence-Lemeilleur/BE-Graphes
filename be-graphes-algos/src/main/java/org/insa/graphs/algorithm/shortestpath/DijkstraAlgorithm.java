package org.insa.graphs.algorithm.shortestpath;

import java.util.*; //tous importer d'un coup
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {
	
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

 public void Initialisation(Graph graph, ArrayList<Label> labels, BinaryHeap<Label> heap, ShortestPathData data ) {
		
        /* Initialisation */
		//On met tout les points à l'infini pour initialiser avec le constructeur
        for(Node node : graph.getNodes()) {
        	labels.add(new Label(node));
        }
      //On ajoute le sommet de départ
        Label Origin = labels.get(data.getOrigin().getId()); //origine de l'arc
        Origin.setCost(0);
        heap.insert(Origin);
        /* Notifie les observateurs du premier évènement (départ de l'origine) */
		notifyOriginProcessed(data.getOrigin());
	}

    @Override
    protected ShortestPathSolution doRun() {
		
     // TODO:
    	
    	final ShortestPathData data = getInputData();
        //boolean dest = false; // pour savoir si on est arrivé à destination ou pas
        //int Taille = graph.size(); //pour avoir la taille du graphe de data
        Graph graph = data.getGraph(); // on récupère le graph de data
        ShortestPathSolution solution = null;
        /* On crée un tableau de Label, avec leur ID : l'indice est le sommet, et la case contient son Label */
        ArrayList<Label> labels = new ArrayList<Label>();
        /* On crée un tas de Label */
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        
      //On appelle l'initialisation comme ça on peu ten redéfinir une autre pour le A* qui viendra l'override
    	Initialisation(graph, labels, heap, data);
		
		// On recupère ce qu'on veut avec getDestination
		
		int nb_succ = 0;
		int nb_visites = 0;
              
		/* Itérations: Tant qu'il existe des sommets non marqués */
        Label x; 
    	while(!heap.isEmpty()) {
    		x=heap.findMin();
    		heap.remove(x);
    		x.setMark();
    	
    		/* On indique aux observateurs que le Node a été marqué */
    		notifyNodeMarked(x.getSommet());
    		System.out.println("Le coût est de " + x.getCost() + "\n");
    		/* On vérifie si on doit s'arrêter, arriver à la destination */
    		if(x.getSommet() == data.getDestination()) { 
    			break;
    		}
    		
			/* Parcours des successeurs du sommet courant */
				for (Arc succ : x.getSommet().getSuccessors()) {
					nb_succ++; 
					// On vérifie que l'on peut réellement prendre cet arc
					if (data.isAllowed(succ)) {
						/* On recupere le label correspondant au noeud dans le tableau de labels */
						Label y=labels.get(succ.getDestination().getId());
						/* On indique aux observateurs que le Node a été marqué */
			    		notifyNodeReached(y.getSommet());
			    		nb_visites++;
						/* Si le successeur n'est pas encore marqué */
						if (y.getMark()==false){
							
							/* Si on obtient un meilleur coût */
							/* Alors on le met à jour */
							if(y.getCost()>x.getCost() + succ.getLength()) {
								if(y.getFather()!=null) {
									heap.remove(y);
								}
								y.setCost(x.getCost()+succ.getLength());
								heap.insert(y);
								y.setFather(succ);
							}
						}
   
					}
			
				}
				
				System.out.print("Nombre de successeurs du sommet: " + nb_succ + "\n");
				System.out.print("Nombre de successeurs visités: " + nb_visites + "\n");
				System.out.print("Le tas est toujours valide? :  " + heap.isValid() + "\n");
				nb_succ = 0;
				nb_visites = 0;
    	}
    	//On crée la solution de fin 
    	if (labels.get(data.getDestination().getId()).getFather() == null) {
    		// La destination n'a pas de prédecesseur, la solution n'est pas faisable
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);      
            
        } else {
        	notifyDestinationReached(data.getDestination());
        	
        	//on recréé le chemin depuis la destination 
        	/*On crée une liste avec tout les prédecesseurs d'un sommet, on garde les arcs */
        	ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels.get(data.getDestination().getId()).getFather();
            while (arc != null) {
                arcs.add(arc);
                arc = labels.get(arc.getOrigin().getId()).getFather();
            }

            // on doit inverser le chemin trouver puisque pour l'instant on a reformer le chemin depuis la destination 
            Collections.reverse(arcs);

            // on met ca dans solution 
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
    	return solution;
    }
}
