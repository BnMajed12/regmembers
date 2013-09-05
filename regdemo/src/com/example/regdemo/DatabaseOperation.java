package com.example.regdemo;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseOperation extends SQLiteOpenHelper{
	SQLiteDatabase dbs,readers;
	private  Cursor c;
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME="vikoba";
	private static final String HUDUMA_TABLE="huduma";
	private static final String MUDA_TABLE="muda";
	private static final String MKOA_TABLE="mikoa";
	private static final String WILAYA_TABLE="wilaya";
	private static final String KATA_TABLE="kata";
	public DatabaseOperation(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	//creating database connections
    db.execSQL("CREATE TABLE "+HUDUMA_TABLE+"(id integer primary key autoincrement, huduma VARCHAR,UNIQUE (huduma))");
	db.execSQL("CREATE TABLE "+MUDA_TABLE+"(id integer primary key autoincrement, muda VARCHAR,UNIQUE (muda) )");
	db.execSQL("CREATE TABLE "+MKOA_TABLE+"(id integer primary key autoincrement, mkoa VARCHAR,UNIQUE (mkoa))");
	db.execSQL("CREATE TABLE "+WILAYA_TABLE+"(id integer primary key autoincrement,mkoa_id integer, wilaya VARCHAR, UNIQUE (wilaya))");
	db.execSQL("CREATE TABLE "+KATA_TABLE+"(id integer primary key autoincrement,wilaya_id integer,kata VARCHAR, UNIQUE (kata))");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
		
	}
	
	public Boolean insertHuduma(String huduma){
		Boolean exists=dataExist(HUDUMA_TABLE,"huduma",huduma);
		if(exists){
			int myid=getNewId(HUDUMA_TABLE);
		dbs=getWritableDatabase();
		dbs.execSQL("INSERT INTO "+HUDUMA_TABLE+" VALUES('"+myid+"','"+huduma+"')");
		dbs.close();
		}
	
		return true;
	}
	
	public Boolean insertMuda(String muda){

		Boolean exists=dataExist(MUDA_TABLE,"muda",muda);
		if(!exists){
			int myid=getNewId(MUDA_TABLE);
		dbs=getWritableDatabase();
		dbs.execSQL("INSERT INTO "+MUDA_TABLE+" VALUES('"+myid+"','"+muda+"')");
		dbs.close();
		}
		
		return true;
	}
	
	public Boolean insertMkoa(String mkoa){
		Boolean exists=dataExist(MKOA_TABLE,"mkoa",mkoa);
		if(!exists){
			int myid=getNewId(MKOA_TABLE);
		dbs=getWritableDatabase();
		dbs.execSQL("INSERT INTO "+MKOA_TABLE+" VALUES('"+myid+"','"+mkoa+"')");
		dbs.close();
		}
		
		return true;
	}
	
	public Boolean insertWilaya(String wilaya,String mkoa){
		Boolean exists=dataExist(WILAYA_TABLE,"wilaya",wilaya);
		if(!exists){
			int myid=getNewId(WILAYA_TABLE);
		readers=getReadableDatabase();
		int id=getMkoaId(mkoa);
		dbs=getWritableDatabase();
		dbs.execSQL("INSERT INTO "+WILAYA_TABLE+" VALUES('"+myid+"','"+id+"','"+wilaya+"')");
		dbs.close();

		}
		return true;
		
	}
	
	public Boolean insertKata(String kata, String wilaya){
		Boolean exists=dataExist(KATA_TABLE,"kata",kata);
		if(!exists){
			int myid=getNewId(KATA_TABLE);
			int id=getWilayaId(wilaya);
		readers=getReadableDatabase();
		dbs=getWritableDatabase();
		dbs.execSQL("INSERT INTO "+KATA_TABLE+" VALUES('"+myid+"','"+id+"','"+kata+"')");
		
		dbs.close();
		
		}
		return true;	
	}
	
	public ArrayList<String> getHuduma(){
		readers=getReadableDatabase();
		String[] args={"none"};
		ArrayList<String> list=new ArrayList<String>();
		c=readers.rawQuery("SELECT huduma FROM "+HUDUMA_TABLE+" where huduma!=?", args);
		if(c.moveToFirst()){
			while(c.isAfterLast()==false){
			String id=c.getString(c.getColumnIndex("huduma"));
			list.add(id);
			c.moveToNext();
			}
		}
		c.close();
	return list;	
	}
	
	public int getMkoaId(String mkoa){
		readers=getReadableDatabase();
		String[] args={mkoa};
		int id=0;
		c=readers.rawQuery("SELECT id FROM "+MKOA_TABLE+ " WHERE mkoa=?", args);
		if(c.moveToFirst()){
			id=c.getInt(c.getColumnIndex("id"));
		}
		c.close();
		return id;
		
	}
	
	public int getWilayaId(String wilaya){
		readers=getReadableDatabase();
		String[] args={wilaya};
		int id=0;
		
		c=readers.rawQuery("SELECT id FROM "+WILAYA_TABLE+ " WHERE wilaya=?", args);
		if(c.moveToFirst()){
			id=c.getInt(c.getColumnIndex("id"));
		}
		c.close();
		return id;
	}
	
	public ArrayList<String> getMuda(){
		readers=getReadableDatabase();
		ArrayList<String> list=new ArrayList<String>();
		String[] args={"none"};
		c=readers.rawQuery("SELECT muda FROM "+MUDA_TABLE+" where muda!=?", args);
		if(c.moveToFirst()){
			while(c.isAfterLast()==false){
			String id=c.getString(c.getColumnIndex("muda"));
			list.add(id);
			c.moveToNext();
			}
		}	
		c.close();
	return list;	
	}
	
	public ArrayList<String> getMkoa(){
		readers=getReadableDatabase();
		ArrayList<String> list=new ArrayList<String>();
		String[] args={"none"};
		c=readers.rawQuery("SELECT mkoa FROM "+MKOA_TABLE+" where mkoa!=? ",args );
		if(c.isAfterLast()==false){
			while(c.moveToNext()){
			String id=c.getString(c.getColumnIndex("mkoa"));
			list.add(id);
			c.moveToNext();
			}
		}
		c.close();
	return list;	
	}
	
	public ArrayList<String> getWilaya(String mkoa){
		readers=getReadableDatabase();
		int id=getMkoaId(mkoa);
		String[] ids={""+id,"none"};
		ArrayList<String> list=new ArrayList<String>();
		c=readers.rawQuery("SELECT wilaya FROM "+WILAYA_TABLE+ " WHERE mkoa_id=? and wilaya!=?", ids);
		if(c.moveToFirst()){
			while(c.isAfterLast()==false){
			String wilaya=c.getString(c.getColumnIndex("wilaya"));
			list.add(wilaya);
			c.moveToNext();
			}
		}
		c.close();
	return list;	
	}
	
	public ArrayList<String> getKata(String wilaya){
		readers=getReadableDatabase();
		int id=getWilayaId(wilaya);
		String[] ids={""+id,"none"};
		ArrayList<String> list=new ArrayList<String>();
		c=readers.rawQuery("SELECT kata FROM "+KATA_TABLE+ " WHERE wilaya_id=? and kata!=?", ids);
		if(c.moveToFirst()){
			while(c.isAfterLast()==false){
			String kata=c.getString(c.getColumnIndex("kata"));
			list.add(kata);
			c.moveToNext();
			}
		}
		c.close();
	return list;	
	}
	
	public Boolean dataExist(String table, String field,String value){
		readers=getReadableDatabase();
		Boolean exists=false;
		String[] ids={""+value};
		c=readers.rawQuery("SELECT "+field+" FROM "+table+ " WHERE "+field+"=?", ids);
		if(c.getCount()>0){
			exists=true;
		}
		c.close();
		return exists;
	}
	
	public int getNewId(String table){
		readers=getReadableDatabase();
		int exists=0;
		c=readers.rawQuery("SELECT id FROM "+table, null);
		if(c.getCount()>0 && c.moveToFirst()){
			exists=c.getCount();
		}
		c.close();
		return exists+1;
	}
	
}
