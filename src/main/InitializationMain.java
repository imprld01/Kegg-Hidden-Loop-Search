package main;

import java.io.IOException;

import dataProcess.SettingProcess;
import init.Initialization;

public class InitializationMain {
	public static void main(String[] args) throws IOException {
		
		int root = Initialization.DESKTOP;
		String dir = "GeneMapLoop";
		String date = "20170218";
		
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
}