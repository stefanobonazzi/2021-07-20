package it.polito.tdp.yelp.model;

public class Event implements Comparable<Event> {
	
	private int giorno;
	private int intervistatore;
	private User user;
	
	public Event(int giorno, int intervistatore, User user) {
		this.giorno = giorno;
		this.intervistatore = intervistatore;
		this.user = user;
	}

	public int getGiorno() {
		return giorno;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	public int getIntervistatore() {
		return intervistatore;
	}

	public void setIntervistatore(int intervistatore) {
		this.intervistatore = intervistatore;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int compareTo(Event o) {
		return this.giorno - o.getGiorno();
	}
	
}
