package main;

import java.io.File;
import java.io.IOException;

import dataBean.FileNavigator;
import dataBean.Kgi;
import dataProcess.FeedbackTrimer;
import dataProcess.KgiProcess;
import dataProcess.MergeTrimer;
import dataProcess.MiniTrimer;
import dataProcess.RelationRecovery;
import dataProcess.SettingProcess;
import init.Initialization;

public class CreateReportMain {

	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = sp.analyze(sp.parse());
		
		System.out.println("Reading file to construct Kgi Obj...");
		// get Kgi objects from file.
		Kgi kgi = KgiProcess.read(fn);
		System.out.println("Complete constructing Kgi Obj!");
		
		/* TrimMain.java */
		System.out.println("Read to Trim...");
		FeedbackTrimer ft = new FeedbackTrimer(fn);
		ft.execute();
		System.out.println("Complete Trimming...");
		
		/* MergeTrimMain.java */
		System.out.println("Read to Merge...");
		MergeTrimer mrt = new MergeTrimer(fn);
		mrt.save(mrt.merge());
		System.out.println("Complete Merging...");
		
		/* MinTrimMain.java */
		System.out.println("Read to Minimize...");
		MiniTrimer mnt = new MiniTrimer(fn);
		mnt.save(mnt.merge());
		System.out.println("Complete Minimizing...");
		
		/* RecoveryMain.java */
		RelationRecovery rr = new RelationRecovery(kgi, fn);
		System.out.println("Relations Recovering...");
		rr.execute();
		System.out.println("Complete Recovering Relations!");
		System.out.println("Merging Relations Info with Loops...");
		rr.merge();
		System.out.println("Complete Merging Relations Info with Loops!");
	}
}