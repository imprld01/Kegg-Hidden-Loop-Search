package main;

import java.io.IOException;

public class ExecutionMain {
	public static void main(String[] args) throws IOException {
		
		System.out.println("===================================");
		System.out.println("(1) Initialization");
		//InitializationMain.main(null);
		System.out.println("===================================");
		System.out.println("(2) Kegg Parsing");
		//KeggParseMain.main(null);
		System.out.println("===================================");
		System.out.println("(3) Node Name Parsing");
		//NodeNameRetrievalMain.main(null);
		System.out.println("===================================");
		System.out.println("(4) Kgi Constructing");
		//PreProcess.main(null);
		System.out.println("===================================");
		System.out.println("(5) Keyword Filtering");
		//KeyFilter.main(null);
		System.out.println("===================================");
		System.out.println("(6) Feedback Loop Searching");
		/* remember to put keywords into the uk file. */
		//LoopSearch.main(null);
		System.out.println("===================================");
		System.out.println("(7) Report Creating");
		//CreateReportMain.main(null);
		System.out.println("===================================");
	}
}