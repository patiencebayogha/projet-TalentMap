package com.viseo.talentmap.common.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Date;

public class LogUtils {


    public static final int NUMBER_OF_LOGS = 3;
    public static final int SIZE_OF_LOG = 5242880; // 5MB
    /**
     * Variable de test de synchronisation du singleton.
     */
    private static final Object __synchonizedObject = new Object();
    private static final String LOG_FILE_TYPE = ".csv";
    private static final String LOG_FILE_NAME = "log";
    private static final String EXCEPTION_FILE_PATH = "sdcard/TalentMap-log/exception.txt";
    private static final String DIR = "sdcard/TalentMap-log/";
    /**
     * L'instance unique
     */
    private static LogUtils SINGLETON = null;
    private File[] logFiles;
    private int currentFile;

    private LogUtils() {
        creatDirIfNotExist();
        creatFileIfNotExist(EXCEPTION_FILE_PATH);
        currentFile = 0;
        logFiles = new File[NUMBER_OF_LOGS];
        createNewLogFile();
    }

    /**
     * Retourne une unique instance de LogUtils.
     *
     * @return L'instance unique du LogUtils.
     */
    public static LogUtils getInstance() {

        if (SINGLETON == null) {
            synchronized (__synchonizedObject) {
                if (SINGLETON == null) {
                    SINGLETON = new LogUtils();
                }
            }
        }
        return SINGLETON;
    }

    private void createNewLogFile() {
        File file = new File(DIR + LOG_FILE_NAME + (currentFile % NUMBER_OF_LOGS) + LOG_FILE_TYPE);
        try {
            file.createNewFile();
            logFiles[currentFile % NUMBER_OF_LOGS] = file;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add log to a file
     *
     * @param tag  Class from where the log has been called
     * @param info Info about the action
     */

    public synchronized void appendLog(String tag, String info) {

        Log.d(tag, info);
        StringBuilder message = new StringBuilder();
        message.append(new Date());
        message.append(";");
        message.append(tag);
        message.append(";");
        message.append(info);

        registerLog(message.toString());
    }

    /**
     * Add log to a file
     *
     * @param tag  Class from where the log has been called
     * @param info Info about the action
     * @param e    Extra infos
     */
    public synchronized void appendLog(String tag, String info, Throwable e) {

        Log.d(tag, info, e);
        StringBuilder message = new StringBuilder();
        message.append(new Date());
        message.append(";");
        message.append(tag);
        message.append(";");
        message.append(info);
        message.append(";");
        message.append(e.getCause());

        registerLog(message.toString());
    }


    private void registerLog(String log) {
        File logFile = logFiles[currentFile % NUMBER_OF_LOGS];
        if (logFile != null) {
            try {
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                buf.append(log);
                buf.newLine();
                buf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (logFile.length() >= SIZE_OF_LOG) {
                    currentFile += 1;
                    if (currentFile >= NUMBER_OF_LOGS) {
                        logFiles[(currentFile % NUMBER_OF_LOGS)].delete();
                    }
                    createNewLogFile();
                }
            } catch (Exception e) {
                Log.e(getClass().getCanonicalName() + ":registerLogList", "Exception deleting the file", e);
            }
        }
    }

    private void creatDirIfNotExist() {
        File dir = new File(DIR);
        if (!dir.mkdirs()) {
            Log.i(getClass().getCanonicalName(), "Could not create dir");
        }
    }

    private File creatFileIfNotExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void writeException(Context context, Throwable e) {

        try {
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(EXCEPTION_FILE_PATH)));
            pw.append(new Date().toString() + "\n");
            e.printStackTrace(pw);
            pw.append("\n\n");
            pw.flush();
            pw.close();
        } catch (FileNotFoundException e1) {
        }
    }
}
