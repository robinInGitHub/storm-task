package com.cdeledu.main;

import com.cdeledu.storm.controller.DataOfDayController;

public class DataOfDayTaskStart {

	public static void main(String[] args) {
		DataOfDayController dataOfDayController = new DataOfDayController();
		dataOfDayController.studyEffectOfDay();
	}
}
