package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

	public static void main(String[] args) {
		
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		try {
			int argKeyNum = Integer.parseInt(args[1]);
			int wrkSpaceI = argKeyNum + 2;
			int root = Initialization.DESKTOP;
			String dir = "GeneMapLoop";
			String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
			if(args.length == (wrkSpaceI + 2)) {
				switch(args[wrkSpaceI]) {
				case "_USERDIR": root = Initialization.USER_DIR; break;
				case "_USERHOME": root = Initialization.USER_HOME; break;
				}
				date = args[(wrkSpaceI + 1)];
			}
			else if(args.length == (wrkSpaceI + 1)) {
				String arg = args[wrkSpaceI];
				if(arg.charAt(0) == '_') {
					switch(arg) {
					case "_USERDIR": root = Initialization.USER_DIR; break;
					case "_USERHOME": root = Initialization.USER_HOME; break;
					}
				}
				else date = arg;
			}
			
			String species = args[0];
			
			// read setting and complete setting.
			File stg = new Initialization(root, dir, date).getSettingFile();
			SettingProcess sp = new SettingProcess(stg);
			FileNavigator fn = sp.analyze(sp.parse(), species);
			
			System.out.println("Reading file to construct Kgi Obj...");
			// get Kgi objects from file.
			Kgi kgi = KgiProcess.read(fn);
			System.out.println("Complete constructing Kgi Obj!");
			
			for(int index = 2 ; index < wrkSpaceI; ++index) {
				/* TrimMain.java */
				System.out.println("Read to Trim...");
				FeedbackTrimer ft = new FeedbackTrimer(fn, args[index]);
				ft.execute();
				System.out.println("Complete Trimming!");
				
				/* MergeTrimMain.java */
				System.out.println("Read to Merge...");
				MergeTrimer mrt = new MergeTrimer(fn, args[index]);
				mrt.save(mrt.merge());
				System.out.println("Complete Merging!");
				
				/* MinTrimMain.java */
				System.out.println("Read to Minimize...");
				MiniTrimer mnt = new MiniTrimer(fn, args[index]);
				mnt.save(mnt.merge());
				System.out.println("Complete Minimizing!");
				
				/* RecoveryMain.java */
				RelationRecovery rr = new RelationRecovery(fn, kgi, args[index]);
				System.out.println("Relations Recovering...");
				rr.execute();
				System.out.println("Complete Recovering Relations!");
				System.out.println("Merging Relations Info with Loops...");
				rr.merge();
				System.out.println("Complete Merging Relations Info with Loops!");
			}
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
	}
}