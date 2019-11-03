package com.example.a98189.taxi.Utils;

import java.io.File;
import java.io.IOException;

/**
 * 文件夹
 */
public class Class_FileTool {

	public Class_FileTool(){
		
	}
	/**
	 * 在SD卡文件夹下创建目录
	 */
	public File createDir_onSD(String dirName){
		File RootDir = new File(GlobleVariable.SD_CARD_PATH+"/"+GlobleVariable.DATA_SAVE_DIR+"/");
		RootDir.mkdir();
		File dir=new File(GlobleVariable.SD_CARD_PATH+"/"+GlobleVariable.DATA_SAVE_DIR+"/"+dirName+"/");
		dir.mkdir();
		return dir;
	}
	
	/**
	 * 在SD卡上创建文件
	 */
	public File createFile_onSD(String dirName, String fileName) throws IOException {
		File file = new File(GlobleVariable.SD_CARD_PATH+"/"+GlobleVariable.DATA_SAVE_DIR+"/"+dirName+"/"+fileName);
		if(isFileExist(fileName, dirName)){
			file.delete();
		}
		file.createNewFile();
		return file;
	}
	
	/**
	 * 判断文件是否存在
	 */
	public boolean isFileExist(String fileName, String dirName){
		File file=new File(GlobleVariable.SD_CARD_PATH+"/"+GlobleVariable.DATA_SAVE_DIR+"/"+dirName+"/"+fileName);
		return file.exists();
	}
}
