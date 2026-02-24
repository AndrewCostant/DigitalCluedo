package com.cluedo.domain;
public abstract class Card {

	private String name;

	public Card(String name) {
		this.name = name;
	}

	// GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Card o){
		return this.name.equals(o.getName());
	}

	public String toString() {
		return "[" + name + "]";
	}

}