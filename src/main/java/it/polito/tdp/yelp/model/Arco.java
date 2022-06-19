package it.polito.tdp.yelp.model;

public class Arco {

	private User u1;
	private User u2;
	private int n;

	public Arco(User u1, User u2, int int1) {
		this.u1 = u1;
		this.u2 = u2;
		this.n = int1;
	}

	public User getU1() {
		return u1;
	}

	public void setU1(User u1) {
		this.u1 = u1;
	}

	public User getU2() {
		return u2;
	}

	public void setU2(User u2) {
		this.u2 = u2;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

}
