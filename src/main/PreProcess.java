package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import dataBean.FileNavigator;
import dataBean.Kgi;
import dataBean.Kgml;
import dataBean.NameTable;
import dataProcess.KgiProcess;
import dataProcess.KgmlParser;
import dataProcess.SettingProcess;
import fileIO.Reader;
import init.Initialization;

public class PreProcess {
	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		
		// read setting and complete setting.
		File stg = new Initialization(root, dir, date).getSettingFile();
		SettingProcess sp = new SettingProcess(stg);
		FileNavigator fn = sp.analyze(sp.parse());
		
		System.out.println("Reading name table file...");
		Reader r = new Reader(fn.getNameFile());
		Gson gson = new Gson();
		NameTable nt = gson.fromJson(r.readLine(), NameTable.class);
		r.close();
		System.out.println("Complete constructing table!");
		
		System.out.println("Transforming kgml files into Kgml Objs...");
		// transform kgml files into Kgml objects.
		File [] kgmlFiles = fn.getKgmlDir().listFiles();
		ArrayList<Kgml> kgmls = new ArrayList<Kgml>();
		for(File kgmlFile : kgmlFiles){
			KgmlParser kp = new KgmlParser(kgmlFile, nt);
			kgmls.add(kp.parse());
		}
		System.out.println("Complete Transforming kgml files!");
		
		System.out.println("Transforming Kgml objs into Kgi obj...");
		// transform Kgml objects into Kgi object.
		KgiProcess kp = new KgiProcess(kgmls);
		Kgi kgi = kp.merge();
		System.out.println("Complete Transforming Kgml objs!");
		
		System.out.println("Saving Kgi object as file...");
		// save Kgi object as file.
		KgiProcess.save(fn, kgi);
		System.out.println("Complete Saving Kgi object!");
	}
}
