package com.infinitys.logger;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    private static File logFile, traceLogFile;
    private static final StringBuilder logString = new StringBuilder(), traceLogString = new StringBuilder();

    private static boolean logging = true;
    private static Runtime instance;

    private static final Thread memReporter = new Thread(() -> {
        while (logging){
            mem();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Log.e("MemReporter", "Error: '" + e + "'");
            }
        }
    }, "MemReporter");

    public static void init(String logsDir){

        String traceLogName = "trace_log-" + getDate() + ".log";
        String logName = "log-" + getDate() + ".log";
        String startSession = "#### Start session " + getDate() + " " + getTime() + " #####\n\n";

        try {
            instance = Runtime.getRuntime();

            logFile = FileUtils.createFile(logsDir, logName);
            traceLogFile = FileUtils.createFile(logsDir, traceLogName);

            logString.append(startSession);
            System.out.print(startSession);

            traceLogString.append(startSession);
        } catch (FileUtils.FileUtilsException e) {
            Log.ce("LogInit", e.toString());
        }
    }

    public static void enableMemReporter(){
        memReporter.start();
    }

    private static String getTime(){
        @SuppressWarnings("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return formatter.format(date);
    }
    private static String getDate(){
        Date date = new Date();
        @SuppressWarnings("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        return formatter.format(date);
    }

    private static void PushLog(){
        try {
            FileUtils.writeFile(logFile, logString.toString());
        } catch (FileUtils.FileUtilsException e) {
            Log.ce("WriteToLogFile", e.toString());
        }
    }
    private static void PushTrace(){
        try {
            FileUtils.writeFile(traceLogFile, traceLogString.toString());
        } catch (FileUtils.FileUtilsException e) {
            Log.ce("WriteToLogFile", e.toString());
        }
    }

    public static void ce(String tag, String msg){
        if (logging) {
            String logLine = "[  CRITICAL\t]" + "[" + getDate() + " " + getTime() + "] " + tag + ": " + msg + "\n";
            String outLine = ANSI_RED + "[  CRITICAL\t]" + "[" + getDate() + " " + getTime() + "] " + tag + ": " + msg + "\n";
            logString.append(logLine);
            System.out.print(outLine);
            PushLog();
            logging = false;
        }
    }
    public static void e(String tag, String msg){
        if (logging) {
            String logLine = "[\tError\t]" + "[" + getDate() + " " + getTime() + "] " + tag + ": " + msg + "\n";
            String outLine = ANSI_RED + "[\tError\t]" + ANSI_RESET +"[" + getDate() + " " + getTime() + "] " + tag + ": " + msg + "\n";
            logString.append(logLine);
            System.out.print(outLine);
            PushLog();
        }
    }
    public static void i(String tag, String msg){
        if (logging) {
            String logLine = "[\tInfo\t]" + "[" + getDate() + " " + getTime() + "] " + tag + ": " + msg + "\n";
            String outLine = ANSI_GREEN + "[\tInfo\t]" + ANSI_RESET +"[" + getDate() + " " + getTime() + "] " + tag + ": " + msg + "\n";
            logString.append(logLine);
            System.out.print(outLine);
            PushLog();
        }
    }

    public static void d(String tag, String msg){
        if (logging) {
            String logLine = "[\tProc\t]" + "[" + getDate() + " " + getTime() + "] " + tag + ": " + msg + "\n";
            String outLine = ANSI_BLUE + "[\tProc\t]" + ANSI_RESET +"[" + getDate() + " " + getTime() + "] " + tag + ": " + msg + "\n";
            logString.append(logLine);
            System.out.print(outLine);
            PushLog();
        }
    }

    public static void trace(LogObj obj){
        if (logging) {
            Thread th = Thread.currentThread();
            Class<LogObj> objClass = (Class<LogObj>) obj.getClass();
            Method method = objClass.getEnclosingMethod();
            Parameter[] params = method.getParameters();

            String className = objClass.getEnclosingClass().getName();
            String funName = obj.getClass().getEnclosingMethod().getName();
            String[][] args = new String[params.length][3];

            for (int i = 0; i < args.length; i++){
                args[i][0] = params[i].getType().getName();
                args[i][1] = params[i].getName();
                args[i][2] = obj.values[i].toString();
            }


            StringBuilder argsLine = new StringBuilder();
            if (args != null)
                for (String[] arg : args) {
                    argsLine.append(arg[0]).append(":").append(arg[1]).append("='").append(arg[2]).append("' ");
                }
            String logLine = "[" + getDate() + " " + getTime() + "][" +th.getName() + ":" + className + "]  " + funName + "(" + argsLine + ")\n";
            traceLogString.append(logLine);
            System.out.print(ANSI_PURPLE + "[\tTRACE\t]" + ANSI_RESET + logLine);
            PushTrace();
        }
    }

    private static void mem(){
        int mb = 1024 * 1024;
        String logLine = "[\tMEM\t\t]" + "[" + getDate() + " " + getTime() + "] " + "Used: " + (instance.totalMemory() - instance.freeMemory()) / mb + " mb, Free: " + instance.totalMemory() / mb + "mb\n";
        String outLine = ANSI_YELLOW + "[\tMEM\t\t]" + ANSI_RESET + "[" + getDate() + " " + getTime() + "] " + "Used: " + (instance.totalMemory() - instance.freeMemory()) / mb + " mb, Free: " + instance.totalMemory() / mb + "mb\n";
        logString.append(logLine);
        System.out.print(outLine);
        PushLog();
    }
}
