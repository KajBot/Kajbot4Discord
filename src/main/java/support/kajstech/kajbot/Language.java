package support.kajstech.kajbot;


import java.io.*;
import java.util.Properties;

public class Language {

    public static Properties messages = new Properties();
    private static File file = new File("lang.properties");

    static void run() {

        try {
            messages.load(new FileInputStream(file));
        } catch (IOException e) {
            try {
                InputStream in = ClassLoader.getSystemResourceAsStream("lang.properties");
                byte[] buffer = new byte[in.available()];
                OutputStream out = new FileOutputStream(file);
                in.read(buffer);
                out.write(buffer);
                messages.load(new FileInputStream(file));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
