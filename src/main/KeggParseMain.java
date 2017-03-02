package main;

import java.io.File;
import java.io.IOException;

import dataBean.FileNavigator;
import dataProcess.KeggParser;
import dataProcess.SettingProcess;
import fileIO.Copyer;
import init.Initialization;

public class KeggParseMain {
	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		String species = "ko";
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = sp.analyze(sp.parse());
		
		System.out.println("Parsing KGML on KEGG Site...");
		KeggParser kp = new KeggParser(fn, species);
		kp.execute();
		kp.execute(true, false, false, true);
		System.out.println("Complete Parsing KGML on KEGG Site!");
		
		System.out.println("Copying KGML Files...");
		File [] keggFiles = (new File(fn.getKeggDirPath() + File.separator + "kgml")).listFiles();
		for(File keggFile : keggFiles){
			long start = System.nanoTime();
	        Copyer.copy(keggFile, new File(fn.getKgmlDirPath() + File.separator + keggFile.getName()));
	        System.out.println("Time taken by Stream Copy = " + (System.nanoTime() - start));
	        keggFile.delete();
		}
		new File(fn.getKeggDirPath() + File.separator + "kgml").delete();
		System.out.println("Complete Copying KGML Files!");
	}
}