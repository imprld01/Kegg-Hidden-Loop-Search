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

public class KeyFilter {
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
		
		System.out.println("Filtering all input keys...");
		// filter all keys.
		int limit = 5;	// L0(1)-L1(2)-L2(3)-L3(4)-L4(5) -> limit = 5
		ArrayList<String> allkeys = InputProcess.parse(fn.getSrcFile()).getKeys();
		ArrayList<String> valkeys = new ArrayList<String>();
		for(String allkey : allkeys){
			System.out.println("Now filtering " + allkey + "...");
			if(InputProcess.isLoopValid(fn, kgi, allkey, allkey, limit)) valkeys.add(allkey);
		}
		InputProcess.write(fn.getKeyFile(), valkeys);
		System.out.println("Complete Filtering keys!");
	}
}