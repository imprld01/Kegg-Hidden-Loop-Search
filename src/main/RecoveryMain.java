package main;

import java.io.File;
import java.io.IOException;

import dataBean.FileNavigator;
import dataBean.Kgi;
import dataProcess.KgiProcess;
import dataProcess.RelationRecovery;
import dataProcess.SettingProcess;
import init.Initialization;

public class RecoveryMain {
	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = sp.analyze(sp.parse(), "ko");
		
		System.out.println("Reading file to construct Kgi Obj...");
		// get Kgi objects from file.
		Kgi kgi = KgiProcess.read(fn);
		System.out.println("Complete constructing Kgi Obj!");
		
		RelationRecovery rr = new RelationRecovery(fn, kgi, "ko:K16857");
		System.out.println("Relations Recovering...");
		rr.execute();
		System.out.println("Complete Recovering Relations!");
		System.out.println("Merging Relations Info with Loops...");
		rr.merge();
		System.out.println("Complete Merging Relations Info with Loops!");
	}
}