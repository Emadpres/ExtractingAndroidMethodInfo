package com.shahin;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseUtils {

	private final String TAG = "DATABASE Utils";
	private String DatabasePath;
	private Logger log;

	private Connection conn;

	private final String DB_NAME = "AndroidMethods";
	private final String CreateDB_Query = "CREATE TABLE IF NOT EXISTS " + DB_NAME
			+ " (ID INTEGER PRIMARY KEY AUTOINCREMENT, APIv INTEGER, PackageName VARCHAR,"
			+ "ClassTypeID INTEGER, ClassName VARCHAR, MethodAccessLevelID INTEGER, IsMethodStatic INTEGER,"
			+ "MethodName VARCHAR, nArgs INTEGER, returnType VARCHAR, argTypes VARCHAR, shortDescription VARCHAR,"
			+ " LongDescription TEXT);";

	public DataBaseUtils(String databasePath, Logger log) {
		this.DatabasePath = "jdbc:sqlite:" + databasePath + File.separator + "AndroidMethodDatabase.db";
		this.log = log;
		File file = new File(databasePath);
		file.mkdirs();
		log.Log(TAG, "database url is:" + this.DatabasePath);
	}

	public void checkDB() {
		connect();
		if(conn == null){
			log.Log(TAG,"can not connect to data base");
			return;
		}
		try {
			Statement statement = conn.createStatement();
			statement.execute(CreateDB_Query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.Log(TAG,"error in check db function  - " + e.getMessage());
		}
		
	}
	
	public void addRecord(RowDataObject obj) {
		String sql = "INSERT INTO " + DB_NAME
				+ "(APIv,PackageName,ClassTypeID,ClassName,MethodAccessLevelID,IsMethodStatic,MethodName,"
				+ "nArgs,returnType,argTypes,shortDescription,LongDescription) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, obj.getAPIv());
			ps.setString(2, obj.getPackageName());
			ps.setInt(3, obj.getClassTypeID());
			ps.setString(4, obj.getClassName());
			ps.setInt(5, obj.getMethodAccessLevelID());
			ps.setInt(6, obj.isMethodStatic()?1:0);
			ps.setString(7, obj.getMethodName());
			ps.setInt(8, obj.getnArgs());
			ps.setString(9, obj.getReturnType());
			ps.setString(10, obj.getArgTypes());
			ps.setString(11, obj.getShortDescription());
			ps.setString(12, obj.getLongDescription());
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.Log(TAG,"error in add record function  - " + e.getMessage());
		}

	}

	private void connect() {
		try {
			conn = DriverManager.getConnection(DatabasePath);
			if(conn == null){
				log.Log(TAG, "error during connecting to db");
				return;
			}
			DatabaseMetaData meta =conn.getMetaData();
			log.Log(TAG, "driver name is : "+ meta.getDriverName());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
