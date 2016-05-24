package com.cdeledu.main;

import com.cdeledu.storm.controller.InitOnceController;

public class InitOnceTaskStart {

	public static void main(String[] args) {
		InitOnceController initOnceController = new InitOnceController();
		initOnceController.init();
	}
}
