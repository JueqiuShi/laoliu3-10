package com.example.a98189.taxi.Utils;

import android.os.Environment;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 保存用户数据线程(第一次登录时使用)
 *
 */


public class ClassSavePersonData extends Thread {

    /**
     * 传过来的用户uid
     */
    private String uid;
    /**
     * 保存到的文件夹名
     */
    private String saveDirName;
    /**
     * 保存的文件名
     */
    private String saveFileName;
    /**
     * 文件操作工具类
     */
    private Class_FileTool fileTool;


    /**
     * @param uid       保存的数据
     * @param saveDirName  保存到哪个文件夹
     * @param saveFileName 以什么文件名保存
     */
    public ClassSavePersonData(String uid, String saveDirName, String saveFileName) {
        this.uid = uid;
        this.saveDirName = saveDirName;
        this.saveFileName = saveFileName;
        fileTool = new Class_FileTool();
    }

    /**
     * 清理旧的文件
     *
     */
    public int cleanOldFile(String curDirName) {
        //删除已有的数据
        File deleteFile = new File(GlobleVariable.SD_CARD_PATH + "/" + GlobleVariable.DATA_SAVE_DIR + "/" + curDirName + "/");

        //删除最旧的文件
        System.out.println("Clean : " + deleteFile);
        deleteFile.delete();

        return 0;
    }

    /**
     * 保存用户数据
     *
     * @throws IOException
     */
    private void SavePersondata(String dirName, String fileName) throws IOException {
        //先判断是否有内存卡
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {

            File Personfile = null;

            fileTool.createDir_onSD(dirName);
            try {
                Personfile = fileTool.createFile_onSD(dirName, fileName);
            } catch (IOException e) {
                System.out.println("Create file Error ------>" + e.toString());
            }

            OutputStream outputStream = null;
            try {
                    outputStream = new FileOutputStream(Personfile);
            } catch (FileNotFoundException e) {
                System.out.println("new FileOutputStream(Personfile) error----->" + e.toString());
            }
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

            try {

                    dataOutputStream.writeBytes(uid);

            } catch (Exception e) {
                System.out.println("Write Person data to file error------->" + e.toString());
            } finally {
                dataOutputStream.close();
            }

        } else {
            //提示没有内存卡
        }
    }

    @Override
    public void run() {
        try {
            SavePersondata(saveDirName, saveFileName);
        } catch (IOException e) {
            System.out.println("SavePersonData(saveDirName, saveFileName) error ------->" + e.toString());
        }

    }

}