package com.shahin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	
	BufferedWriter writer;
	
	
	public Logger(String pATH) {
		try {
			File f = new File(pATH + File.separator + "log.txt");
			if(!f.exists())
				f.createNewFile();
			writer = new BufferedWriter(new FileWriter(f));
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void info(String str){
		System.out.println(str);
	}


	public void Log(String tag,String str){
		System.out.println(tag + " : " + str);
		TextLog(tag + " : " + str);
	}
	
	public void TextLog(String str){
		try {
			writer.write(str + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close(){
		try {
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
