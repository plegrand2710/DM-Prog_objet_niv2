package cursed_chronicles.Player;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileLoader {
    public static String[] loadFileNames(String... paths) {
        List<String> fileNames = new ArrayList<>();

        for (String path : paths) {
            File directory = new File(path);
            if (directory.exists() && directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            fileNames.add(file.getName());
                        }
                    }
                }
            }
        }
        return fileNames.toArray(new String[0]);
    }
}
