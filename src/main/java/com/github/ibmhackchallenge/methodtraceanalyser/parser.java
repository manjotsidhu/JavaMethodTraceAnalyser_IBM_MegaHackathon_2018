package com.github.ibmhackchallenge.methodtraceanalyser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.*;
import org.apache.commons.io.FileUtils;


public class parser {
	
	String log1;
	String log2;
	
	ArrayList log11 = new ArrayList();
	
	public void parse(File log1File, File log2File) throws IOException {
		this.log1 = FileUtils.readFileToString(log1File);
		this.log2 = FileUtils.readFileToString(log2File);
		
		
	}
	
}