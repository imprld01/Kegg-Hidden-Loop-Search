package main;

import java.io.File;
import java.io.IOException;

import dataBean.FileNavigator;
import dataProcess.MiniTrimer;
import dataProcess.SettingProcess;
import init.Initialization;

public class MinTrimMain {
	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = sp.analyze(sp.parse());
		
		System.out.println("Read to Minimize...");
		MiniTrimer mt = new MiniTrimer(fn);
		mt.save(mt.merge());
		System.out.println("Complete Minimizing...");
	}
}