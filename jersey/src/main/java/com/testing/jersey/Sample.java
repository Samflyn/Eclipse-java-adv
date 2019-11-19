package com.testing.jersey;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Sample {
	@Override
	public String toString() {
		return "Sample [no=" + no + ", name=" + name + ", num=" + num + "]";
	}

	private int no;
	private String name;
	private int num;

	public Sample() {

	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
