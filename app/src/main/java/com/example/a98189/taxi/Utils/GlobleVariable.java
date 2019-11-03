package com.example.a98189.taxi.Utils;

import android.os.Environment;

/**
 *
 */
public class GlobleVariable {


	/**
	 * SD卡路径名
	 */
	public final static String SD_CARD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	/**
	 * 数据保存的文件夹
	 */
	public final static String DATA_SAVE_DIR = "GduptCar";

	/**
	 * 用户的uid
	 */
	public static String UID ;
}
