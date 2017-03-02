package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import dataBean.FileNavigator;
import dataBean.NameTable;
import dataProcess.KgmlParser;
import dataProcess.NodeNameRetrieval;
import dataProcess.SettingProcess;
import fileIO.Writer;
import init.Initialization;

public class NodeNameRetrievalMain {

	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = sp.analyze(sp.parse());
		
		System.out.println("Retrieving Node Names...");
		// transform kgml files into Kgml objects.
		File [] kgmlFiles = fn.getKgmlDir().listFiles();
		ArrayList<String> names = new ArrayList<String>();
		for(File kgmlFile : kgmlFiles){
			KgmlParser kp = new KgmlParser(kgmlFile, null);
			ArrayList<String> result = kp.parseEntry();
			for(String name : result)
				if(!names.contains(name)) names.add(name);
		}
		
		NameTable nt = new NameTable();
		for(String name : names) {
			if(name.contains("(")) continue;
			else if(!name.contains("path") && !name.contains("PATH")) {	/* path:ko01052 have PATH case! */
				String nodeName = NodeNameRetrieval.nameReplace(NodeNameRetrieval.getGeneName(name.split(":")[1]));
				nt.addElement(name, nodeName);
			}
		}
		System.out.println("Complete Retrieving Node Names!");
		
		System.out.println("Constructing Name Table...");
		Writer w = new Writer(fn.getNameFile());
		Gson gson = new Gson();
		w.write(gson.toJson(nt));
		w.close();
		System.out.println("Complete Constructing Name Table!");
	}
}
