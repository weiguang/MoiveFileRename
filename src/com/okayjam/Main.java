package com.okayjam;

import com.okayjam.utils.FileUtils;
import com.okayjam.utils.RegexUtils;

import java.io.File;

/**
 * Created by Weiguang Chen <chen2621978@gmail.com> on 2018/5/6 10:04.
 */
public class Main {
    String replaceString[] = {"飘花电影","piaohua.com","阳光电影","www.ygdy8.com","电影天堂","paiohua.com","飞鸟娱乐","bbs.hdbird.com"};

    static String path = "M:\\的士快速";
//    static String path = "L:\\test";
    public static void main (String[] args) {
        File file = new File(path);
        new Main().iterAllFile(file);
    }

    public void iterAllFile(File file){
        if(file == null) return;
        File[] fileList = file.listFiles();
        for (File file1 : fileList) {
            if(file1 == null  || !file1.exists()) continue;
//            System.out.println(file1.getName());
            if(file1.getName().startsWith("$")) continue;
            String newName =  getName(file1);
            if(!newName.equals(file1.getName())){
                System.out.println(file1.getName() + " -> " +newName);
                FileUtils.rename(file1, newName);
            }
            if(file1.isDirectory()){
               iterAllFile(file1);
            }

        }
    }


    public String getName(File file) {
        String oldName =  file.getName();
        String newName = oldName;
        int start = oldName.indexOf('[');
        int end = -1;
        if(start>=0) {
            end = oldName.indexOf(']');
        }else {
            start = oldName.indexOf('【');
            if(start >=0 ) {
                end = oldName.indexOf('】');
            }
        }
        if(start >=0 && end > start) {
            String s = oldName.substring(start, end);
            String s1 = s.replaceAll("((w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?","");
            if(s1.length()  != s.length()){
                newName = oldName.substring(0,start) + oldName.substring(end+1);
            }
        }
        newName = newName.replaceAll("^[\\.\\-]+", "");
        for (String rs : replaceString) {
            newName = newName.replaceAll(rs, "");
        }

        return newName;
    }

}
