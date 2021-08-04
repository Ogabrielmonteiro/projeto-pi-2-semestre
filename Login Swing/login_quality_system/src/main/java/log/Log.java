/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 *
 * @author carlo
 */
public class Log {
    
    public String pegarLog() throws FileNotFoundException, IOException {
        String textoFinal = "";
        
        FileReader fileReader = new FileReader("/home/ubuntu/Desktop/Quality-System/jar/jarProjeto/log.txt");
        BufferedReader reader = new BufferedReader(fileReader);
        String data = null;
        while ((data = reader.readLine()) != null) {
            textoFinal += (data + "\n");
        }
        
        return textoFinal;
    }

    public void logar(String texto) throws FileNotFoundException, IOException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        
        String textoFinal = "";

        FileReader fileReader = new FileReader("/home/ubuntu/Desktop/Quality-System/jar/jarProjeto/log.txt");
        BufferedReader reader = new BufferedReader(fileReader);
        String data = null;
        while ((data = reader.readLine()) != null) {
            textoFinal += (data + "\n");
        }
        

        FileWriter arq = new FileWriter("/home/ubuntu/Desktop/Quality-System/jar/jarProjeto/log.txt");
        PrintWriter gravarArq = new PrintWriter(arq);
        String str = textoFinal + date + " " + time + " " + texto;
        gravarArq.println(str);

        System.out.println("Novo log escrito: " + textoFinal + date + " " + time + " " + texto);
        
        arq.close();
        fileReader.close();
        reader.close();
    }
}
