package com.example.regdemo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptManager {

	
	public String EncryptMd5(String toEncrypt){
		  try {
			MessageDigest md5=MessageDigest.getInstance("MD5");
			   byte[] toMd=toEncrypt.getBytes();
			   md5.update(toMd);
			 String  Encrypted=arrToString(md5.digest());
			 return Encrypted;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return null;
	}
	 private static String arrToString(byte[] b){
	        String res;
	        StringBuilder sb=new StringBuilder(b.length*2);
	        for(int i=0; i<b.length; i++){
	            int j=b[i]&0xff;
	            if(j<16){
	                sb.append('0');
	            }
	            sb.append(Integer.toHexString(j));
	        }
	        res=sb.toString();
	        return res.toUpperCase();
	    }
	 
	 
}
