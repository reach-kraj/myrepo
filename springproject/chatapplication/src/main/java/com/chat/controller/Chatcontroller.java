package com.chat.controller;

import com.chat.entity.Model;

public class Chatcontroller {
	
	 public static void main(String[] args) {
		Model m = new Model();
		m.setEmail("this is a test");
		System.out.println(m.getEmail());
	}
}
