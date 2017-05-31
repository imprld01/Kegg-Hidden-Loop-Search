package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dataBean.FileNavigator;
import dataBean.Kgi;
import dataProcess.FeedbackTrimer;
import dataProcess.InputProcess;
import dataProcess.KgiProcess;
import dataProcess.MergeTrimer;
import dataProcess.MiniTrimer;
import dataProcess.RelationRecovery;
import dataProcess.SettingProcess;
import init.Initialization;
import search.dfsTreeSearch;

public class FilterSearchReportMain {
	public static void main(String[] args) {
		
		String batchName = args[0];
		int argKeyNum = Integer.parseInt(args[3]);
		int wrkSpaceI = argKeyNum + 4;
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
		if(args.length == (wrkSpaceI + 2)) {
			switch(args[wrkSpaceI]) {
			case "_USERDIR": root = Initialization.USER_DIR; break;
			case "_USERHOME": root = Initialization.USER_HOME; break;
			}
			date = args[wrkSpaceI + 1];
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
		
		String species = args[1];
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = null;
		Kgi kgi = null;
		try {
			fn = sp.analyze(sp.parse(), species);
			System.out.println("Reading file to construct Kgi Obj...");
			// get Kgi objects from file.
			kgi = KgiProcess.read(fn);
			System.out.println("Complete constructing Kgi Obj!");
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println();
		System.out.println("===KeyFilter=========================");
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		try {
			System.out.println("Filtering all input keys...");
			// filter all keys.
			int limit = Integer.parseInt(args[2]);	// L0(1)-L1(2)-L2(3)-L3(4)-L4(5) -> limit = 5
			ArrayList<String> allkeys = InputProcess.parse(fn.getSrcFile(batchName)).getKeys();
			ArrayList<String> valkeys = new ArrayList<String>();
			for(int index = 4; index < wrkSpaceI; ++index)
				if(!allkeys.contains(args[index])) allkeys.add(args[index]);
			for(String allkey : allkeys){
				System.out.println("Now filtering " + allkey + "...");
				if(InputProcess.isLoopValid(fn, kgi, allkey, allkey, limit)) valkeys.add(allkey);
				System.out.println("Complete filtering " + allkey + "!");
			}
			InputProcess.write(fn.getKeyFile(batchName), valkeys);
			System.out.println("Complete Filtering keys!");
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		System.out.println();
		
		//================================================================================================================================
		
		System.out.println("===LoopSearchMain=====================");
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		try {
			// search by selected keys.
			int limit = Integer.parseInt(args[2]);	// A(1)-B(2)-C(3)-D(4)-A(5) -> limit = 5
			dfsTreeSearch dfs = new dfsTreeSearch(fn, kgi, limit);
			ArrayList<String> usekeys = InputProcess.parse(fn.getKeyFile(batchName)).getKeys();
			for(int index = 4; index < wrkSpaceI; ++index)
				if(!usekeys.contains(args[index])) usekeys.add(args[index]);
			for(String usekey : usekeys){
				System.out.println("Start Searching " + usekey + " Loops...");
				long start = System.nanoTime();
				//dfs.init(usekey, 127); // for 127:Over. condition
				dfs.init(usekey);
				dfs.search();
				System.out.println("Time taken by Searching = " + (System.nanoTime() - start));
				System.out.println("Complete Searching " + usekey + " Loops...");
			}
			
			fn.getKeyFile(batchName).delete();
			fn.getSrcFile(batchName).delete();
			fn.getKeyFile(batchName).getParentFile().delete();
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		//================================================================================================================================
		
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		try {
			for(int index = 4 ; index < wrkSpaceI; ++index) {
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