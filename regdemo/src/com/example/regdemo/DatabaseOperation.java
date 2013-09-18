package com.example.regdemo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


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
	private static final String PROFILE_TABLE="profile";
	private static final String FAMILY_TABLE="family";
	private static final String MDHAMINI_TABLE="mdhamini";
	public DatabaseOperation(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
	//creating database connections
	String profile="CREATE TABLE "+PROFILE_TABLE+" (id integer primary key autoincrement, ";
	       profile+=" jina VARCHAR,jinsia VARCHAR, kuzaliwa VARCHAR, mkoa VARCHAR,wilaya VARCHAR,kata VARCHAR,";
	       profile+="simu VARCHAR,namba_kitambulisho VARCHAR, aina_kitambulisho VARCHAR,huduma VARCHAR,kiwango_ombi VARCHAR,";
	       profile+="kiwango_kuanzia VARCHAR, malipo_siku VARCHAR,malipo_muda VARCHAR,biashara VARCHAR, kikundi VARCHAR,imageurl VARCHAR)";
	       
	String family="CREATE TABLE "+FAMILY_TABLE+" (id integer primary key autoincrement,owner integer, jina VARCHAR,aina_familia VARCHAR )";
	String mdhamini="CREATE TABLE "+MDHAMINI_TABLE+"(id integer primary key autoincrement, owner integer,";
	       mdhamini+="jina VARCHAR, simu VARCHAR, imageurl VARCHAR)";
    db.execSQL(profile);
    db.execSQL(family);
    db.execSQL(mdhamini);
    db.execSQL("CREATE TABLE "+HUDUMA_TABLE+"(id integer primary key autoincrement, huduma VARCHAR,UNIQUE (huduma))");
	db.execSQL("CREATE TABLE "+MUDA_TABLE+"(id integer primary key autoincrement, muda VARCHAR,UNIQUE (muda) )");
	db.execSQL("CREATE TABLE "+MKOA_TABLE+"(id integer primary key autoincrement, mkoa VARCHAR,UNIQUE (mkoa))");
	db.execSQL("CREATE TABLE "+WILAYA_TABLE+"(id integer primary key autoincrement,mkoa_id integer, wilaya VARCHAR, UNIQUE (wilaya))");
	db.execSQL("CREATE TABLE "+KATA_TABLE+"(id integer primary key autoincrement,wilaya_id integer,kata VARCHAR, UNIQUE (kata))");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	
		
	}
	
	
	public int insertData(String[] data,String tablename){
		int myid=getNewId(tablename);
	String insert="INSERT INTO "+tablename+" VALUES('"+myid+"',";
	for(int i=0; i<data.length; i++){
    insert+="'"+data[i]+"'";
    if(data.length!=(i+1)){
    insert+=",";	
    }
	}
	insert+=")";
	dbs=getWritableDatabase();
	dbs.execSQL(insert);
	dbs.close();
	return myid;
	}
	
	/**
	 * This is used to insert online result to some table for updates
	 * @param data
	 * @param tablename
	 * @param fields
	 * @param value
	 */
	public void insertFromOnline(String[] data, String tablename,String fields, String value){
		Boolean exists=dataExist(tablename,fields,value);
		if(!exists){
			int myid=getNewId(tablename);
			String insert="INSERT INTO "+tablename+" VALUES('"+myid+"',";
			for(int i=0; i<data.length; i++){
		    insert+="'"+data[i]+"'";
		    if(data.length!=(i+1)){
		    insert+=",";	
		    }
			}
			insert+=")";
			dbs=getWritableDatabase();
			dbs.execSQL(insert);
			dbs.close();	
		}
	}
	
	public void updateData(HashMap<String,Object> fieldPlusData,String tablename,String conditionField,String conditionValue){
		Set<String> fields=fieldPlusData.keySet();
     //   int size=fields.size()/2;
        Iterator<String> iterate=fields.iterator();
        String query="update  "+tablename+"  set ";
       Object value="";
        while(iterate.hasNext()){
             value=iterate.next();
            if(iterate.hasNext()){
             query+=""+value+"='"+fieldPlusData.get(value)+"', ";   
           
            }
          
        }
        query+=""+value+"='"+fieldPlusData.get(value)+"'";
        query+=" where "+conditionField+"='"+conditionValue+"'";
        dbs=getWritableDatabase();
        dbs.execSQL(query);
        dbs.close();
	}
	
	
	public Boolean insertHuduma(String huduma){
		Boolean exists=dataExist(HUDUMA_TABLE,"huduma",huduma);
		if(!exists){
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
		c=readers.rawQuery("SELECT id FROM "+table+" order by id desc limit 1", null);
		if(c.getCount()>0 && c.moveToFirst()){
			exists=c.getInt(0);
		}
		c.close();
		return exists+1;
	}
	
	public Cursor getFomu(String table){
		readers=getReadableDatabase();	
		c=readers.rawQuery("SELECT id as _id,jina,imageurl FROM "+table, null);
		if(c.moveToFirst()){
			Log.e("cursor name",c.getString(c.getColumnIndex("jina")));
		return c;
		}
		return null;
	}
	
	public Cursor getFomuData(String idField,String id,String table){
		readers=getReadableDatabase();
		
		String[] ids={id};
		c=readers.rawQuery("SELECT * FROM "+table+ " WHERE "+idField+"=?", ids);
		if(c.moveToFirst()){
	     return c;
		}
		return null;
	}
	
	public int deleteFomuData(String table,String idField,String id){
		dbs=getWritableDatabase();
		String[] ids={id};
	int aff=dbs.delete(table, idField+"=?", ids);
	
	return aff;
	}
}
