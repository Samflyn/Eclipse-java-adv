package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.bean.Items;

public class TESTING {
	public static void main(String[] args) {
		List<Items> i = new ArrayList<Items>();
		Items ii = new Items();
		ii.setId(1);
		i.add(ii);
		ii = new Items();
		ii.setId(2);
		i.add(ii);
		ii = new Items();
		ii.setId(3);
		i.add(ii);
		ii = new Items();
		ii.setId(4);
		i.add(ii);
		ii = new Items();
		ii.setId(5);
		i.add(ii);
		Optional<Items> any = i.stream().filter(iii -> iii.getId()==3).findAny();
		System.out.println(any);
	}
}
