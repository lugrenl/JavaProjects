package fileapi;

import java.io.File;
import java.io.IOException;

/**
 * Можем использовать один из конструкторов для создания объекта:
 * File(String путь_к_каталогу)
 * File(File каталог, String имя_файла)
 * File(String каталог, String имя_файла)
 */

public class FileApi
{
    //работа с каталогами
    public static void main(String[] args)
    {

        //определяем объект для каталога
        File dir = new File("C:\\Program Files");
        //если объект представляет каталог
        if (dir.isDirectory())
        {
            //получаем все вложенные объекты в каталоге
            for (File item : dir.listFiles())
            {
                if (item.isDirectory())
                {
                    System.out.println(item.getName() + " - каталог");
//                    for (File item2 : item.listFiles())
//                    {
//                        if (item2.isDirectory())
//                        {
//                            System.out.println(item2.getName() + " - каталог");
//                        } else
//                        {
//                            System.out.println(item2.getName() + " - файл");
//                        }
//                    }
                }
                else
                {
                    System.out.println(item.getName() + " - файл");
                }
            }
        }
    }
}

class FileApi2 {

    public static void main(String[] args) {

        //определяем объект для каталога
        File myFile = new File("src\\main\\resources\\document.txt");
        System.out.println("File name: " + myFile.getName());
        System.out.println("Parent folder: " + myFile.getParent());

        if(myFile.exists()) {
            System.out.println("File exists: " + myFile.exists());
        } else {
            System.out.println("File doesn't exist");
        }

        System.out.println("Absolute path: " + myFile.getAbsolutePath());
        System.out.println("File size: " + myFile.length());

        if (myFile.canRead()) {
            System.out.println("File can be read: " + myFile.canRead());
        } else {
            System.out.println("File can't be read");
        }

        if (myFile.canWrite()) {
            System.out.println("File can be written: " + myFile.canWrite());
        } else {
            System.out.println("File can't be written");
        }

        //создадим новый файл
        File newFile = new File("src\\main\\resources\\newFile.txt");
        try {
            boolean created = newFile.createNewFile();
            if (created) {
                System.out.println("File was created");
            } else {
                System.out.println("File wasn't created");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println(newFile.getAbsolutePath());
        System.out.println(newFile.length());
        System.out.println(newFile.canRead());
        System.out.println(newFile.canWrite());
        System.out.println(newFile.exists());
        System.out.println(newFile.isFile());
        System.out.println(newFile.isDirectory());
        System.out.println(newFile.isHidden());
        System.out.println(newFile.lastModified());
        System.out.println(newFile.getParent());
        System.out.println(newFile.getPath());
    }
}
