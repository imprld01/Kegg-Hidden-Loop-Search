package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dataBean.FileNavigator;
import dataProcess.KeggParser;
import dataProcess.SettingProcess;
import fileIO.Copyer;
import init.Initialization;

public class KeggParseMain {
	public static void main(String[] args) {
		
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
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
			
			System.out.println("Parsing KGML on KEGG Site...");
			System.out.println("(It may take about 10 minutes.)");
			System.out.println("(If there is no response after about 10 mins, maybe you can restart this program.)");
			KeggParser kp = new KeggParser(fn, species);
			kp.execute();
			kp.execute(true, false, false, true);
			System.out.println("Complete Parsing KGML on KEGG Site!");
			
			System.out.println("Copying KGML Files...");
			File kf = new File(fn.getKeggDirPath() + File.separator + "kgml");
			File [] keggFiles = kf.listFiles();
			for(File keggFile : keggFiles){
				long start = System.nanoTime();
		        Copyer.copy(keggFile, new File(fn.getKgmlDirPath() + File.separator + keggFile.getName()));
		        System.out.println("Time taken by Stream Copy = " + (System.nanoTime() - start));
		        keggFile.delete();
			}
			kf.delete();
			System.out.println("Complete Copying KGML Files!");
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
			System.out.println("If there is no .stg file,"
					+ "it means you lose the setting file!");
			System.out.println("To solve this problem,"
					+ "maybe you can run the InitializationMain.bat first.");
			System.out.println("If it shows connect time out,"
					+ "please check whether your internet connection is fine or not.");
			System.out.println("If your internet connection is fine,"
					+ "maybe you can try to run again after a few minutes.");
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
	}
}