package com.shahin;

public class RowDataObject {
	
	private int APIv;
	private String PackageName;
	private int ClassTypeID;
	private String ClassName;
	private int MethodAccessLevelID;
	private boolean MethodStatic;
	private String MethodName;
	private int nArgs;
	private String returnType;
	private String argTypes;
	private String shortDescription;
	private String LongDescription;
	public int getAPIv() {
		return APIv;
	}
	public void setAPIv(int aPIv) {
		APIv = aPIv;
	}
	public String getPackageName() {
		return PackageName;
	}
	public void setPackageName(String packageName) {
		PackageName = packageName;
	}

	public int getClassTypeID() {
		return ClassTypeID;
	}
	public void setClassTypeID(int classTypeID) {
		ClassTypeID = classTypeID;
	}
	public int getMethodAccessLevelID() {
		return MethodAccessLevelID;
	}
	public void setMethodAccessLevelID(int methodAccessLevelID) {
		MethodAccessLevelID = methodAccessLevelID;
	}
	public String getClassName() {
		return ClassName;
	}
	public void setClassName(String className) {
		ClassName = className;
	}

	public boolean isMethodStatic() {
		return MethodStatic;
	}
	public void setMethodStatic(boolean methodStatic) {
		MethodStatic = methodStatic;
	}
	public String getMethodName() {
		return MethodName;
	}
	public void setMethodName(String methodName) {
		MethodName = methodName;
	}
	public int getnArgs() {
		return nArgs;
	}
	public void setnArgs(int nArgs) {
		this.nArgs = nArgs;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public String getArgTypes() {
		return argTypes;
	}
	public void setArgTypes(String argTypes) {
		this.argTypes = argTypes;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getLongDescription() {
		return LongDescription;
	}
	public void setLongDescription(String longDescription) {
		LongDescription = longDescription;
	}
	
	

}
