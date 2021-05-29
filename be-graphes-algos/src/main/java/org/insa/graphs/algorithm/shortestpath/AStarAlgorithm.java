package org.insa.graphs.algorithm.shortestpath;

//import java.awt.Point;
import org.insa.graphs.model.Point;
import java.util.ArrayList; 
//import org.insa.graphs.algorithm.shortestpath.Label;
//import org.insa.graphs.algorithm.shortestpath.LabelStar;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {

	public AStarAlgorithm(ShortestPathData data) {
		super(data);
	}
	
	//DISTINCTION: Pile=Tas : premier entré dernier sorti, le tas se tris tout seul, par ordre croissant ou décroissant
	//             File: premier entré, premier sorti
	
	//RAPPEL: 
	//Le dijkstra a aucune idée de où est l'arrivée
	//Donc il va chercher dans toutes les directions jusqu'à ce qu'il trouve la destination

	//A* lui il a une vague idée parce qu'on lui dit à chaque fois qu'il visite un point si celui ci est éloigné ou pas de la destination
	//Du coup les points visités qui l'éloigneront de la destination auront tendance à être tout en haut de la pile (donc peu ou pas visitié car ceux de distance min seront en bas)
	//Il va donc plus vite à la destination
	
	final public void Initialisation(Graph graph, ArrayList<Label> labels, BinaryHeap<Label> heap, ShortestPathData data) {
		
		for (Node node: graph.getNodes()) {
			LabelStar a = new LabelStar(node, data);
			
			Node sommet_a = a.getSommet();
			Point point_a = sommet_a.getPoint();
			//System.out.print("point_a: " + point_a + "\n");
			Node dest = data.getDestination();
			Point dest_point =dest.getPoint();
			Double dist = point_a.distanceTo(dest_point);
			
			//On fait un if pour avoir le calcul en distance et le calcul en temps de trajet

			if (data.getMode() == AbstractInputData.Mode.LENGTH) {
				a.setEstimation(dist);
				labels.add(a);
			}
			else {
				//on utilise v=d/t
				a.setEstimation(dist / data.getGraph().getGraphInformation().getMaximumSpeed());
				labels.add(a);
			}
		}
		LabelStar labelOrigin = (LabelStar) labels.get(data.getOrigin().getId()); //On aura alors l'origine de l'arc
		labelOrigin.setCost(0);
		heap.insert(labelOrigin);
	}

}