package it.polito.tdp.yelp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {

	//Queue of events
	private PriorityQueue<Event> queue;
	
	//Parameters
	private int x2;
	
	//Output 
	private int totGiorni;
	private Map<Integer, Integer> intervistePerIntervistatore;
	
	//World status
	private Graph<User, DefaultWeightedEdge> graph;
	private List<User> users;
	private List<User> intervistati;
	
	public Simulator(Graph<User, DefaultWeightedEdge> graph) {
		this.graph = graph;
	}
	
	public void initialize(int x1, int x2) {
		this.queue = new PriorityQueue<Event>();
		this.totGiorni = 0;
		this.intervistePerIntervistatore = new TreeMap<Integer, Integer>();
		this.users = new ArrayList<User>(this.graph.vertexSet());
		this.intervistati = new ArrayList<User>();
		this.x2 = x2;
		
		for(int i=1; i<=x1; i++) {
			User user = this.users.get((int) (Math.random()*this.users.size()));
			this.users.remove(user);
			this.intervistati.add(user);
			this.intervistePerIntervistatore.put(i, 1);
			Event e = new Event(1, i, user);
			this.queue.add(e);
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			this.processEvent(e);
		}
	}
	
	public void processEvent(Event e) {
		this.totGiorni = e.getGiorno();
		double p = Math.random();
		
		if(p<=0.6 || e.getUser() == null) {
			if(this.intervistati.size() < this.x2) {		
				List<User> possibili = new ArrayList<User>();
				if(e.getUser() != null)
				possibili = new ArrayList<User>(this.getSimilarita(e.getUser()).keySet());
				User user;
				
				if(possibili.isEmpty()) {
					user = this.users.get((int) (Math.random()*this.users.size()));
				} else {
					user = possibili.get((int) (Math.random()*possibili.size()));
				}
			
				int n = this.intervistePerIntervistatore.get(e.getIntervistatore());
				this.intervistePerIntervistatore.put(e.getIntervistatore(), n+1);
				this.users.remove(user);
				this.intervistati.add(user);
				Event next = new Event(e.getGiorno()+1, e.getIntervistatore(), user);
				this.queue.add(next);
			}
		} else if(p>0.6 && p<=0.8) {
			Event next = new Event(e.getGiorno()+1, e.getIntervistatore(), null);
			this.queue.add(next);
		} else {
			Event next = new Event(e.getGiorno()+1, e.getIntervistatore(), e.getUser());
			this.queue.add(next);
		}
		
	}
	
	public Map<User, Integer> getSimilarita(User user) {
		int w = 0;
		Map<User, Integer> result = new HashMap<>();
		List<User> vicini = Graphs.neighborListOf(this.graph, user);
		if(vicini.isEmpty())
			return result;
		
		for(User u: vicini) {
			if(this.users.contains(u)) {
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
		}
		return result;
	}

	public int getTotGiorni() {
		return totGiorni;
	}

	public Map<Integer, Integer> getIntervistePerIntervistatore() {
		return intervistePerIntervistatore;
	}

}
