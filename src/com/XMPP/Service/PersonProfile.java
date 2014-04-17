package com.XMPP.Service;

import java.io.Serializable;

public class PersonProfile implements Serializable {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
