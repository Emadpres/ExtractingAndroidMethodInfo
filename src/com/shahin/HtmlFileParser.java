package com.shahin;

import java.awt.RadialGradientPaint;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// myElement.select("tbody > tl > tr.api"); ==means==> Considering "myElement" scope, find those "tr" tags which has "api" class and has a "tl" parent and "tbody" grandparent.


public class HtmlFileParser {

	private Document doc;
	private Logger log;
	private int ver = 24;
	private List<String[]> longDesc;
	private DataBaseUtils db;
	private String packageName;
	private String className;
	private int classType;
	private final String TAG = "HTML FILE PAESER";

	public HtmlFileParser(Logger log, String packageName, File htmlFile, DataBaseUtils db) {
		this.db = db;
		this.log = log;
		this.packageName = packageName;
		longDesc = new ArrayList<>();
		try {
			doc = Jsoup.parse(htmlFile, "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getData() {

		Elements className = doc.select("h1.api-title");
		if (className == null || className.size() == 0) {
			log.Log(TAG, packageName + " -- in class name parsing");
			return;
		}
		this.className = className.first().text();

		Elements signiture = doc.select("p > code.api-signature");
		if (signiture == null || signiture.size() == 0) {
			log.Log(TAG, packageName + " -- in class name parsing");
			return;
		}
		String s = signiture.first().text();
		if (s.contains("abstract"))
			classType = 1;
		else if (s.contains("interface"))
			classType = 2;
		else if (s.contains("enum"))
			classType = 3;
		else
			classType = 0;

		Elements longDescDiv = doc.select("div.api > h3.api-name");
		for (Element e : longDescDiv) {
			String method = e.text();
			String desc = "";
			for (Element e2 : e.parent().select("p")) {
				if (e2.select("b").size() > 0)
					continue;
				if (desc != "")
					desc += " ";
				desc += e2.text();
				// System.out.println(e2.text());
			}
			longDesc.add(new String[] { method, desc });
		}

		Element cons = doc.getElementById("pubctors");
		if (cons != null) {
			Elements p = cons.select("tbody >tr.api");
			if (p != null)
				showAllConstructors(p);
		}

		Element publicMethods = doc.getElementById("pubmethods");
		if (publicMethods != null) {
			Elements p = publicMethods.select("tbody >tr.api");
			if (p != null)
				showAllMethods(p, 0);
		}

		Element protectMethods = doc.getElementById("promethods");
		if (protectMethods != null) {
			Elements protects = protectMethods.select("tbody >tr.api");
			if(protects != null)
				showAllMethods(protects, 1);
		}

	}

	private void showConstructor(Elements childs) {

	}

	private void showAllConstructors(Elements childs)
	{
		String methodRet = "void", methodName = className;
		for (Element e : childs)
		{
			String desc = "", args = "", longText = "";
			Element signatureElement = e.select("td > code").first();
			args = signatureElement.text();

			Element shortDescElement = e.select("td > p").first();
			if (shortDescElement!=null)
				desc = shortDescElement.text();

			for (String[] s : longDesc) {
				if (s[0].trim().equals(methodName.trim())) {
					longText = s[1];
					break;
				}
			}

			//System.out.println(">>>>\t\t"+methodRet + " - " + methodName + " - " + args + "  | " + desc);

			RowDataObject object = new RowDataObject();
			object.setAPIv(ver);
			object.setPackageName(packageName);
			object.setClassTypeID(classType);
			object.setClassName(className);
			object.setMethodAccessLevelID(0); //public
			object.setMethodStatic(false);
			object.setMethodName(methodName);
			object.setnArgs(countArgs(args));
			object.setReturnType(methodRet);
			object.setArgTypes(argsText(args));
			object.setShortDescription(desc);
			object.setLongDescription(longText);
			db.addRecord(object);
		}
	}

	private void showAllMethods(Elements childs, int methodType) {

		for (Element e : childs) {
			String methodRet = "", methodName = "", desc = "", args = "", longText = "";
			methodRet = e.select("td > code").first().text();
			methodName = e.select("td > code").get(1).select("a").first().text();
			args = e.select("td > code").get(1).text();
			Elements shortDescriptionArray = e.select("td").get(1).select("p");
			for (Element de : shortDescriptionArray) {
				desc += de.text();
			}
			//System.out.println(">>>>\t\t"+methodRet + " - " + methodName + " - " + args + "  | " + desc);

			for (String[] s : longDesc) {
				if (s[0].trim().equals(methodName.trim())) {
					longText = s[1];
					break;
					//System.out.println(longText);
				}
			}

			// System.out.println(longText + "\n");

			RowDataObject object = new RowDataObject();
			object.setAPIv(ver);
			object.setPackageName(packageName);
			object.setClassTypeID(classType);
			object.setClassName(className);
			object.setMethodAccessLevelID(methodType);
			object.setMethodStatic(methodRet.contains("static") ? true : false);
			object.setMethodName(methodName);
			object.setnArgs(countArgs(args));
			object.setReturnType(methodRet.replace("final", "").replace("static", "").trim());
			object.setArgTypes(argsText(args));
			object.setShortDescription(desc);
			object.setLongDescription(longText);
			db.addRecord(object);
		}
	}

	private int countChar(String str, CharSequence ch) {
		return str.length() - str.replace(ch, "").length();
	}
	
	private int countArgs(String str) {
		String main = str.substring(str.indexOf("(")+1, str.lastIndexOf(")"));
		if(main.equals(""))
			return 0;
		String[] args = main.split(",");
		return args.length;
	}

	private String argsText(String str) {
		String main = str.substring(str.indexOf("(")+1, str.lastIndexOf(")"));
		if(main.equals(""))
			return "";
		String[] args = main.split(",");
		String res = "";
		for (int i=0 ; i<args.length; i++) {
			args[i] = args[i].trim();
			int p = args[i].indexOf(" ");
			if(p>=0)
				res += args[i].substring(0, p) + ((i+1==args.length)?"":",");
		}
		return res;
	}

}
