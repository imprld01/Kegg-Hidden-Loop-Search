package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import dataBean.FileNavigator;
import dataBean.Kgi;
import dataProcess.InputProcess;
import dataProcess.KgiProcess;
import dataProcess.SettingProcess;
import init.Initialization;
import search.dfsTreeSearch;

public class LoopSearch {
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
		
		// search by selected keys.
		int limit = 5;	// A(1)-B(2)-C(3)-D(4)-A(5) -> limit = 5
		dfsTreeSearch dfs = new dfsTreeSearch(fn, kgi, limit);
		ArrayList<String> usekeys = InputProcess.parse(fn.getKeyFile()).getKeys();
		for(String usekey : usekeys){
			System.out.println("Start Searching " + usekey + " Loops...");
			long start = System.nanoTime();
			//dfs.init(usekey, 127); // for 127:Over. condition
			dfs.init(usekey);
			dfs.search();
			System.out.println("Time taken by Searching = " + (System.nanoTime() - start));
			System.out.println("Complete Searching " + usekey + " Loops...");
		}
	}
}