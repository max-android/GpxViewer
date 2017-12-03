package com.primerexample.gpxviewer.manager;

import java.io.File;
import java.util.List;

/**
 * Created by Максим on 01.12.2017.
 */

public class FileFinder {


    public List<String> search(File directory, List<String> res, String search) {


        //получаем список всех объектов в текущей директории
        File[] list = directory.listFiles();

        //просматриваем все объекты по-очереди
        for (int i = 0; i < list.length; i++) {
            //если это директория
            if (list[i].isDirectory()) {

                //выполняем поиск во вложенных директориях
                search(list[i], res, search);
            }
            //если это файл
            else {
                //...выполняем проверку на соответствие типу объекта
                if (list[i].getName().endsWith(search)) {
                    //...добавляем текущий объект в список результатов,
                    res.add(list[i].getAbsolutePath());
                }
            }

        }

        return res;
    }

}
