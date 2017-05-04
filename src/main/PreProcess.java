package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
	public static void main(String[] args) {
		
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");System.out.println("(It may take about 8 minutes.)");
		
		try {
			int root = Initialization.DESKTOP;
			String dir = "GeneMapLoop";
			String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
			if(args.length == 3) {
				switch(args[1]) {
				case "_USERDIR": root = Initialization.USER_DIR; break;
				case "_USERHOME": root = Initialization.USER_HOME; break;
				}
				date = args[2];
			}
			else if(args.length == 2) {
				String arg = args[1];
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
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
	}
}
