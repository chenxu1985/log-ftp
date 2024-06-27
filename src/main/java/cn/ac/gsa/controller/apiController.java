package cn.ac.gsa.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * author chenx
 */
@RestController
public class apiController {

    @RequestMapping("/api/log/{db}")
    public String getApiLog(@PathVariable("db") String db, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        String result= "";
        if(db.equals("gsa")){
            result = this.getGsaLog();
        }
        return result;
    }
    public String getGsaLog() {
        String result = "";
        String Path = "/disk/webdb/csdb/logs/gsalogs/";
//        String Path = "/Users/laphael/Desktop/test/";
        File dir = new File(Path);
        String fileName = "";
        fileName = this.getLastModifiedFileName(dir);
        fileName = Path + fileName;
        java.nio.file.Path path = Paths.get(fileName);
        long lines = 0;
        try {
            lines = Files.lines(path).count();
            result = Files.lines(path).skip(lines - 2).findFirst().get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
    public static String getLastModifiedFileName(File directory) {
        File[] files = directory.listFiles();
        long lastModifiedTime = Long.MIN_VALUE;
        File lastModifiedFile = null;
        for (File file : files) {
            if (file.lastModified() > lastModifiedTime) {
                lastModifiedTime = file.lastModified();
                lastModifiedFile = file;
            }
        }
        return lastModifiedFile.getName();
    }
}
