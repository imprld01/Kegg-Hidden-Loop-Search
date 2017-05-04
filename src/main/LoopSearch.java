package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dataBean.FileNavigator;
import dataBean.Kgi;
import dataProcess.InputProcess;
import dataProcess.KgiProcess;
import dataProcess.SettingProcess;
import init.Initialization;
import search.dfsTreeSearch;

public class LoopSearch {
	public static void main(String[] args) {
		
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		try {
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
			FileNavigator fn = sp.analyze(sp.parse(), species);
			
			System.out.println("Reading file to construct Kgi Obj...");
			// get Kgi objects from file.
			Kgi kgi = KgiProcess.read(fn);
			System.out.println("Complete constructing Kgi Obj!");
			
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
	}
}