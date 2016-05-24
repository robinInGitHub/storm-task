package com.cdeledu.main;

public class IndexTaskStart {
	public static void main(String[] args) {
		String sign = "0";
		if (args != null && args.length > 0) {
			sign = args[0];
		}
		if ("1".equals(sign)) {
			AverageTaskStart.main(args);
			return;
		}
		if ("2".equals(sign)) {
			DataOfDayTaskStart.main(args);
			return;
		}
		if ("3".equals(sign)) {
			InitOnceTaskStart.main(args);
			return;
		}
		AverageTaskStart.main(args);
		DataOfDayTaskStart.main(args);
	}
}
