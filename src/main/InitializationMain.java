package main;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dataProcess.SettingProcess;
import init.Initialization;

public class InitializationMain {
	public static void main(String[] args) {
		
		System.out.println("Start on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		System.out.println("=====================================");
		
		try {
			int root = Initialization.DESKTOP;
			String dir = "GeneMapLoop";
			String date = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDate.now());
			if(args.length == 2) {
				switch(args[0]) {
				case "_USERDIR": root = Initialization.USER_DIR; break;
				case "_USERHOME": root = Initialization.USER_HOME; break;
				}
				date = args[1];
			}
			else if(args.length == 1) {
				String arg = args[0];
				if(arg.charAt(0) == '_') {
					switch(arg) {
					case "_USERDIR": root = Initialization.USER_DIR; break;
					case "_USERHOME": root = Initialization.USER_HOME; break;
					}
				}
				else date = arg;
			}
			
			Initialization init = new Initialization(root, dir, date);
			
			init.settingInitialize(
					"Database", "Keyword", "Report", "Kegg_Kgml_Source", "Kegg_Pathway_Kgml", "Kgml_Information",
					"Entry_Data", "Kgml_Info.ki", "Name_Table.ed", "All_Keyword.ak", "Use_Keyword.uk",
					"Feedback_Loop.fl", "Loop_Existence.le", "Layer_Information.li", "Node_Information.ni",
					"Place_Path.pph", "Trim_Report.tr", "Min_Report.mr", "Merge_Report.mgr", "Relation_Report.rlr",
					"Relation_Loop.rlp");
			
			SettingProcess sp = new SettingProcess(init.getSettingFile());
			sp.analyze(sp.parse());
		}
		catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
		System.out.println("=====================================");
		System.out.println("Finish on " + DateTimeFormatter.ofPattern("E yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
	}
}