package com.shahin;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	
	//private final static String SDK_PATH = "E:\\Android\\SDK_nov_2016";
    private final static String SDK_PATH = "/Users/emadpres/Library/Android/sdk";

    private final static String DOCS_PATH = SDK_PATH + File.separator + "docs"+File.separator +"reference";

	//private final static String OUTPUT_PATH = "D:\\database_outpus";
	private final static String OUTPUT_PATH = "./Results/";
	
	private static List<String> sources;

	public static void main(String[] args) {

		sources = new ArrayList<>();
		//File file = new File(SDK_PATH + File.separator + "sources"+File.separator +"android-24");

        File file = new File(DOCS_PATH);
		System.out.println(file.getPath());
		for(File f : file.listFiles(new HtmlFileFilter())){
			if(f.isDirectory())
				getItem(f,DOCS_PATH);
			//else
				//sources.add(f.getName());
		}

        Logger logger = new Logger(OUTPUT_PATH);

		DataBaseUtils db = new DataBaseUtils(OUTPUT_PATH, logger);
		db.checkDB();


        /*
        //File f = new File("/Users/emadpres/Library/Android/sdk/docs/reference/java/util/concurrent/TimeUnit.html");
        //File f = new File("/Users/emadpres/Library/Android/sdk/docs/reference/android/animation/RectEvaluator.html");
        File f = new File("/Users/emadpres/Library/Android/sdk/docs/reference/android/accessibilityservice/AccessibilityServiceInfo.html");
        HtmlFileParser parser = new HtmlFileParser(logger,"java.util.concurrent.TimeUnit", f, db);
        parser.getData();*/

		int i = 0;
		for(String htmlFilesPath : sources){
			File f = new File(htmlFilesPath);
			if(f.exists() && !htmlFilesPath.endsWith("package-summary.html")){
			    String packageName  = htmlFilesPath.substring(DOCS_PATH.length()+1, htmlFilesPath.length()-".html".length());
			    packageName = packageName.replace('/','.');

				HtmlFileParser parser = new HtmlFileParser(logger, packageName, f, db);
				parser.getData();
			}
			logger.info(i +" / " + sources.size());
			i++;
		}


		
		logger.close();
	}
	
	private static void getItem(File parent, String parentPath) {
		for (File f : parent.listFiles(new HtmlFileFilter())) {
			if (f.isDirectory())
				getItem(f, parentPath + (parentPath.equals("") ? "" : "/") + parent.getName());
			else
				sources.add(parentPath + (parentPath.equals("") ? "" : "/") +parent.getName() + "/"+ f.getName());
		}
	}

    private static class HtmlFileFilter implements FileFilter{
        @Override
        public boolean accept(File pathname) {
            // TODO Auto-generated method stub
            return pathname.isDirectory() | pathname.getName().endsWith(".html");
        }
    }
	private static class JavaFileFilter implements FileFilter{
		@Override
		public boolean accept(File pathname) {
			// TODO Auto-generated method stub
			return pathname.isDirectory() | pathname.getName().endsWith(".java");
		}
	}
	
	

}
