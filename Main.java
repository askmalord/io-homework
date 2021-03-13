import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static StringBuilder fileCreateInfo = new StringBuilder();
    public static void main(String[] args) {
        // задача 1
        File games = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomeWork\\src\\Games");
        if (games.mkdir()) {
            writeInfo(games);
        }
        File src = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\src");
        if (src.mkdir()) {
            writeInfo(src);
        }
        File res = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\res");
        if (res.mkdir()) {
            writeInfo(res);
        }
        File savegames = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\savegames");
        if (savegames.mkdir()) {
            writeInfo(savegames);
        }
        File temp = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\temp");
        if (temp.mkdir()) {
            writeInfo(temp);
        }
        File main = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\src\\main");
        if (main.mkdir()) {
            writeInfo(main);
        }
        File test = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\src\\test");
        if (test.mkdir()) {
            writeInfo(test);
        }
        File mainJava = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\src\\main\\Main.java");
        try {
            if (mainJava.createNewFile()) {
                writeInfo(mainJava);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        File utilsJava = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\src\\main\\Utils.java");
        try {
            if (utilsJava.createNewFile()) {
                writeInfo(utilsJava);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        File drawables = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\res\\drawables");
        if (drawables.mkdir()) {
            writeInfo(drawables);
        }
        File vectors = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\res\\vectors");
        if (vectors.mkdir()) {
            writeInfo(vectors);
        }
        File icons = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\res\\icons");
        if (icons.mkdir()) {
            writeInfo(icons);
        }
        File tempTxt = new File("C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\temp\\temp.txt");
        try {
            if (tempTxt.createNewFile()) {
                writeInfo(tempTxt);
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(tempTxt);
            writer.write(fileCreateInfo.toString());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        // проверка работоспособности кода 2 задачи
        List<String> massOfFiles = new ArrayList<>();
        GameProgress gameProgress1 = new GameProgress(100, 5, 24, 34.05);
        GameProgress gameProgress2 = new GameProgress(80, 2, 16, 15.0);
        GameProgress gameProgress3 = new GameProgress(60, 3, 22, 32.05);
        String fileName1 = "C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\savegames\\save1.dat";
        String fileName2 = "C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\savegames\\save2.dat";
        String fileName3 = "C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\savegames\\save3.dat";
        saveGame(fileName1, gameProgress1);
        saveGame(fileName2, gameProgress2);
        saveGame(fileName3, gameProgress3);
        massOfFiles.add(fileName1);
        massOfFiles.add(fileName2);
        massOfFiles.add(fileName3);
        String zipFileName = "C:\\Users\\Maksim\\IdeaProjects\\ioHomework\\src\\Games\\savegames\\zipGameProgress.zip";
        zipFiles(zipFileName, massOfFiles);
        // удаление файлов после упаковки в zip
        for (File file: savegames.listFiles()) {
            if (!file.getName().contains(".zip")) {
                file.delete();
                //
            }
        }
        // проверка работоспособности кода 3 задачи
        openZip(zipFileName); // распаковка архива
        GameProgress gameProgress = openProgress(fileName2);
        System.out.println(gameProgress);
    }

    public static void writeInfo(File someFile) {
        if (someFile.isDirectory()) {
            fileCreateInfo.append("Каталог " + someFile.getName() + " создан\n");
        }
        else {
            fileCreateInfo.append("Файл " + someFile.getName() + " создан\n");
        }
    }
    // сохранение игрового процесса
    public static void saveGame(String fileName, GameProgress gameProgress) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
            objectOutputStream.writeObject(gameProgress);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    // упаковка zip-архива
    public static void zipFiles(String fileName, List<String> filesForZip) {
        try (FileOutputStream outputStream = new FileOutputStream(fileName);
             ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream)){
            for (int i = 0; i < filesForZip.size(); i++) {
                try (FileInputStream inputStream = new FileInputStream(filesForZip.get(i))) {
                    ZipEntry someEntry = new ZipEntry(filesForZip.get(i));
                    zipOutputStream.putNextEntry(someEntry);
                    byte[] massOfBytes = new byte[inputStream.available()];
                    inputStream.read(massOfBytes);
                    zipOutputStream.write(massOfBytes);
                    zipOutputStream.closeEntry();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
    // распаковка zip-архива
    public static void openZip(String zipFileName) {
        try(FileInputStream fileInputStream = new FileInputStream(zipFileName);
            ZipInputStream zipInputStream = new ZipInputStream(fileInputStream)) {
            ZipEntry entry;
            String fileName;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                fileName = entry.getName();
                FileOutputStream outputStream = new FileOutputStream(fileName);
                for (int c = zipInputStream.read(); c != -1 ; c = zipInputStream.read()) {
                    outputStream.write(c);
                }
                outputStream.flush();
                zipInputStream.closeEntry();
                outputStream.close();
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    // восстановление игрового процесса
    public static GameProgress openProgress(String fileName) {
        GameProgress someGameProgress = null;
        try(FileInputStream inputStream = new FileInputStream(fileName);
            ObjectInputStream objInput = new ObjectInputStream(inputStream)) {
            someGameProgress = (GameProgress) objInput.readObject();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return someGameProgress;
    }
}