package com.testing.jersey;

import java.util.ArrayList;
import java.util.List;

public class TestRepository {
	List<Sample> samp;

	public TestRepository() {
		samp = new ArrayList<Sample>();
		Sample s1 = new Sample();
		s1.setNo(1);
		s1.setName("sam");
		s1.setNum(2);
		Sample s2 = new Sample();
		s2.setNo(2);
		s2.setName("sam1");
		s2.setNum(3);
		samp.add(s1);
		samp.add(s2);
	}

	public List<Sample> getSamples() {
		return samp;
	}

	public Sample getSample(int no) {
		Sample s1 = null;
		for (Sample s : samp) {
			if (s.getNo() == no) {
				return s;
			}
		}
		return s1;
	}

	public void create(Sample s1) {
		samp.add(s1);
	}
}
