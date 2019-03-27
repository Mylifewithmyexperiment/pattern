/**
 * 
 */
package com.elitecorelib.core;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.elitecorelib.core.security.BASE64Decoder;


class LicenseUtility {
	private static final char[] PASSWORD = "enfldsgbnlsngdlksdsgm".toCharArray();
	private static final String ENCRYPTION_ALGORITHM="PBEWithMD5AndDES";
	private static final byte[] SALT = { (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, };
	private static SecretKey  key;
	static{    	    			
		BASE64Decoder decoder = new BASE64Decoder();   	 
		byte[] encodedKey = null;
		try {
			
				encodedKey = decoder.decodeBuffer(CoreConstants.LICENSE_SECURE_KEY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(encodedKey!=null)
			key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "DES"); 
	}
	private static String getDateToString(Date date){
		Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");		
		return formatter.format(date);				
	}
	private static Date getStringToDate(String date) throws ParseException{
		Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return (Date)((DateFormat) formatter).parse(date);							
	}
	public static LicenseData parseLicense() throws Exception{

		String packageName,strNoOfUsage,strLastDateOfUsage;

		int noOfUsage=-1; //unlimited
		Date lastDateOfUsage = null;

		String licenseKey =LibraryApplication.getLibraryApplication().getlibrarySharedPreferences().getString(CoreConstants.ELITECORE_LICENSE_KEY);

		if(licenseKey!=null && !"".equals(licenseKey)){
			String decrypyText = decodeString(licenseKey);
			if(decrypyText.contains("~")){
				StringTokenizer st = new StringTokenizer(decrypyText, "~", false);

				if(st.countTokens()==3){
					packageName = st.nextToken();
					strNoOfUsage = st.nextToken();
					strLastDateOfUsage = st.nextToken();						

					if(strNoOfUsage!=null && !"".equals(strNoOfUsage)){
						try{
							noOfUsage = Integer.parseInt(strNoOfUsage);
						}catch(NumberFormatException nfe){
							return null;
						}
					}

					if(strLastDateOfUsage!=null && !"".equals(strLastDateOfUsage)){
						try{
							lastDateOfUsage = getStringToDate(strLastDateOfUsage);
						}catch(Exception e){
							return null;								
						}
					}
					return new LicenseData(noOfUsage, lastDateOfUsage, packageName, "gt50981");						

				}else{
					return null;
				}				
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

	private static String decodeString(String encryptedString)throws Exception{
		// Get a cipher object.
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, key);

		//decode the BASE64 coded message
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] raw = decoder.decodeBuffer(encryptedString);

		//decode the message
		byte[] stringBytes = cipher.doFinal(raw);

		//converts the decoded message to a String
		String clear = new String(stringBytes, "UTF8");
		return clear;
	}

	public static String decrypt(String property) throws GeneralSecurityException, IOException {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ENCRYPTION_ALGORITHM);
		SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
		Cipher pbeCipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
		return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
	}
	private static byte[] base64Decode(String property) throws IOException {
		return new BASE64Decoder().decodeBuffer(property);
	}
}
