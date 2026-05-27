import java.io.*;
import java.nio.file.*;

public class FileHandler {

    public static boolean fileExists(String path) {
        return Files.exists(Paths.get(path));
    }

    public static String readFile(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
            return null;
        }
    }

    public static void writeFile(String path, String content) {
        try {
            Files.writeString(Paths.get(path), content);
        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}
