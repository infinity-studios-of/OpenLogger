package com.infinitys.logger;

import java.io.*;

public class FileUtils {

    public static class FileUtilsException extends Exception{
        private String operation, file, err;

        public FileUtilsException(String operation, String file, String err) {
            this.operation = operation;
            this.file = file;
            this.err = err;
        }

        @Override
        public String toString() {
            return operation + " with file: '" + file + "' failed, error: '" + err + "'";
        }
    }

    public static void writeFile(File file, String text) throws FileUtilsException {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            throw new FileUtilsException("Write file", file.getAbsolutePath(), e.toString());
        }
    }
    public static File createFile(String path, String fileName) throws FileUtilsException {
        new File(path).mkdirs();
        File f = new File(path + "/" + fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                throw new FileUtilsException("Create file", f.getAbsolutePath(), e.toString());
            }
        }
        return f;
    }
}
