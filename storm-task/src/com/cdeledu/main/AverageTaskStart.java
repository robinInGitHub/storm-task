package com.cdeledu.main;

import com.cdeledu.storm.controller.AverageController;

public class AverageTaskStart {

	public static void main(String[] args) {
		AverageController average = new AverageController();
		average.chapterAVG();
		average.courseAVG();
	}
}
