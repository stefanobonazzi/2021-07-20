package it.polito.tdp.yelp.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import it.polito.tdp.yelp.db.YelpDao;

public class Model {
	
	private YelpDao dao;
	private Map<String, User> mAllUser;
	private Map<String, User> mUser;
	private Graph<User, DefaultWeightedEdge> graph;
	
	public Model() {
		this.dao = new YelpDao();
		this.mAllUser = this.dao.getAllUsers();
		this.mUser = new HashMap<String, User>();
	}
	
	public Set<User> creaGrafo(int n, int year) {
		this.dao.getUserN(n, mAllUser, mUser);
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.graph, this.mUser.values());
		
		List<Arco> archi = this.dao.getArchi(n, year, mUser);
		
		for(Arco a: archi) {
			if(this.graph.vertexSet().contains(a.getU1()) && this.graph.vertexSet().contains(a.getU1())) {
				DefaultWeightedEdge edge = this.graph.addEdge(a.getU1(), a.getU2());
				this.graph.setEdgeWeight(edge, a.getN());
			}
		}
		return this.graph.vertexSet();
	}
	
	public Map<User, Integer> getSimilarita(User user) {
		int w = 0;
		Map<User, Integer> result = new HashMap<>();
		for(User u: Graphs.neighborListOf(this.graph, user)) {
			DefaultWeightedEdge edge = this.graph.getEdge(user, u);
			int tw = (int) this.graph.getEdgeWeight(edge);
			
			if(tw >= w) {
				if(tw > w) {
					result = new HashMap<User, Integer>();
					result.put(u, tw);
					w = tw;
				} else {
					result.put(u, tw);
				}
			}
		}
		return result;
	}

	public Graph<User, DefaultWeightedEdge> getGraph() {
		return graph;
	}

	public String simula(int x1, int x2) {
		Simulator sim = new Simulator(this.graph);
		sim.initialize(x1, x2);
		sim.run();
		
		Map<Integer, Integer> res = sim.getIntervistePerIntervistatore();
		int gg = sim.getTotGiorni();
		
		String s = "Survey completata in "+gg+" giorni\n\n";
		
		for(int i: res.keySet()) {
			s += "Intervistatore n°"+i+" -- n° intervistati: "+res.get(i)+"\n";
		}
		
		return s;
	}
	
}
