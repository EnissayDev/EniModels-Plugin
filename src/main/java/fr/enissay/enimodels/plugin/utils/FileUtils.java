package fr.enissay.enimodels.plugin.utils;

import java.io.*;

public class FileUtils {

    /**
     *
     * This method reads a file and returns its content as a String
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readFile(File file)
    {
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }
        }
        catch(FileNotFoundException e) {
        }
        catch(IOException e) {
        }
        finally {
            try {
                bufferedReader.close();
            }
            catch(IOException e) {
            }
        }
        return stringBuffer.toString();
    }
}
