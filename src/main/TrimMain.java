package main;

import java.io.File;
import java.io.IOException;

import dataBean.FileNavigator;
import dataProcess.FeedbackTrimer;
import dataProcess.SettingProcess;
import init.Initialization;

public class TrimMain {
	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = sp.analyze(sp.parse(), "ko");
		
		System.out.println("Read to Trim...");
		FeedbackTrimer ft = new FeedbackTrimer(fn, "ko:K16857");
		ft.execute();
		System.out.println("Complete Trimming...");
	}
}