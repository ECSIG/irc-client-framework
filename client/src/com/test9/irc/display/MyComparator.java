package com.test9.irc.display;

import java.util.Comparator;

public class MyComparator implements Comparator<String> {

	public int compare(String o1, String o2) {
		return(o1.toLowerCase().compareTo(o2.toLowerCase()));
	}

}
