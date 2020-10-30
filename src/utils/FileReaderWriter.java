package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dongcl
 */
public class FileReaderWriter {
    
    public static String loadFileIntoString(String filepath){
        StringBuilder strb= new StringBuilder();
        try{
        FileInputStream fileInputStream = new FileInputStream(filepath);
        strb.append(IOUtils.toString(fileInputStream,"UTF-8"));
        }catch(IOException ex){
            System.out.println("fileInputStream:" + ex.getMessage());
        }
        return strb.toString();
    }
    
    public static void saveStringInfoToFile(String filepath,String contentToSave){
        try{
        File file = new File(filepath);
        FileUtils.writeStringToFile(file, contentToSave,"UTF-8");
        }catch(IOException ex){
            System.out.println("FileUtils.writeStringToFile" + ex.getMessage());
        }
        
    }
    
}
