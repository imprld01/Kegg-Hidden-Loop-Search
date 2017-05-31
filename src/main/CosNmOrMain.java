package main;

import java.io.File;
import java.io.IOException;

import dataBean.FileNavigator;
import dataProcess.CrossNumOriented;
import dataProcess.SettingProcess;
import init.Initialization;

public class CosNmOrMain {
	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = sp.analyze(sp.parse(), "ko");
		
		System.out.println("Start...");
		CrossNumOriented cno = new CrossNumOriented(fn, "ko:K16857");
		//cno.execute(4);
		cno.subseter();
		System.out.println("Complete!");
	}
}