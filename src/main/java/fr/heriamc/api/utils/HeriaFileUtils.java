package fr.heriamc.api.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public final class HeriaFileUtils {

    public static <T> T fromJsonFile(File file, Gson gson, Class<T> classOfT) {
        if(!file.exists()) return null;
        try (FileReader reader = new FileReader(file, StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, classOfT);
        } catch (IOException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void copyDirSafely(String folder, String path) throws IOException {
        for (File allFiles : Objects.requireNonNull(new File(path).listFiles())) {
            File newFile = new File(folder + "/" + allFiles.getName());
            if (newFile.exists()) {
                newFile.deleteOnExit();
            }
            File file = new File(folder + "/" + allFiles.getName());
            HeriaFileUtils.copyFileRecursive(allFiles, file);
        }
    }

    public static void copyFileRecursive(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists())
                dest.mkdir();
            String[] files = src.list();
            for (String fileName : files) {
                File srcFile = new File(src, fileName);
                File destFile = new File(dest, fileName);
                copyFileRecursive(srcFile, destFile);
            }
        } else {
            fileCopy(src, dest);
        }
    }

    private static void fileCopy(File src, File dest) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = Files.newInputStream(src.toPath());
            out = Files.newOutputStream(dest.toPath());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0)
                out.write(buffer, 0, length);
        } finally {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        }
    }

    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null)
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath()))
                    deleteDir(f);
            }
        file.delete();
    }

}
