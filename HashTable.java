
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Ilmari
 * Note
 */
public class HashTable {

    int koko;
    public arrayObject[] readFile(String fileName, arrayObject[] mainArray){



        int tempkoko = 0;
        try{



         BufferedReader br = new BufferedReader(new FileReader(fileName));
         String readLine;

         while((readLine = br.readLine()) != null){
             tempkoko++;
             int key = Integer.parseInt(readLine.trim());
             int hash = hashFunction(key);
             int foundIndex = findKey(key, mainArray);
             //System.out.println("Avain oli " + key + " hash oli " +hash);
             if(mainArray[hash] == null){
             arrayObject newNode = new arrayObject(Integer.parseInt(readLine.trim()));
             mainArray[hash] = newNode;
             if("setA.txt".equals(fileName)){

                 newNode.foundFromA = true;
                 newNode.A_Line = tempkoko;
             }
             else
                 newNode.foundFromB = true;
             }

             else if(mainArray[hash].value == key) {
                  if("setA.txt".equals(fileName)){
                    mainArray[hash].foundFromA = true;
                  }
                  else{
                    mainArray[hash].foundFromB = true;
                  }
                  mainArray[hash].incrementCounter();
             }

             else if(foundIndex > 0){
                 if(mainArray[foundIndex].foundFromA || mainArray[foundIndex].foundFromB)
                  if("setA.txt".equals(fileName)){
                    mainArray[foundIndex].foundFromA = true;
                  }
                  else{
                    mainArray[foundIndex].foundFromB = true;
                  }
                  mainArray[foundIndex].incrementCounter();

             }
            else{
                while(mainArray[hash] != null){
                    ++hash;
                    hash %= koko;
                }

             arrayObject newNode = new arrayObject(Integer.parseInt(readLine.trim()));
             mainArray[hash] = newNode;
             if("setA.txt".equals(fileName)){
                 newNode.foundFromA = true;
                 newNode.A_Line = tempkoko;
             }
             else
                 newNode.foundFromB = true;

             }
         }

         System.out.println("Reading file " +fileName+ " was succesful");
         return mainArray;
        }

        catch(IOException e){
            System.out.println("Something went wrong, reading file " + fileName + " was not successful");
        }

      return null;
    }
    public int getMaxSize(){
        return koko;
    }
    public int hashFunction(int key){

        return key % getMaxSize();
    }


    public void getMaxLines(String fileA, String fileB) throws IOException{
        List<String> linesA = Files.readAllLines(Paths.get(fileA), Charset.defaultCharset());
        List<String> linesB = Files.readAllLines(Paths.get(fileB), Charset.defaultCharset());
        if(linesA.size() > linesB.size()){
            koko = linesA.size() *2;
        }
        else
            koko = linesB.size() *2;
        //koko = linesA.size() + linesB.size();


    }
    public void union(arrayObject[] array) throws IOException{
        try{
        FileWriter textWriter = new FileWriter(new File("or.txt"));
        ArrayList<String> temp = new ArrayList<>();
        String tempString;
        for(int i = 0; i < array.length; i++){
            if(array[i] != null){
                tempString="";
                int length = String.valueOf(array[i].value).length();
                tempString+= Integer.toString(array[i].value);
                for(int a = 0; a < 10-length; a++){
                    tempString += " ";
                }
                tempString += Integer.toString(array[i].getCounter());
                temp.add(tempString);
            }

        }
        Collections.sort(temp, new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
            int n1 = Integer.parseInt(a.split(" ")[0]);
            int n2 = Integer.parseInt(b.split(" ")[0]);

            return n1 - n2;
        }
        });
        for(String s : temp){
            textWriter.write(s);
            textWriter.write(System.lineSeparator());
        }
        textWriter.close();
            System.out.println("Writing union file succesful");
        }
        catch(IOException e){
            System.out.println("Failed to save file");
        }
    }
    public int findKey(int key, arrayObject[] array){
        int arrayHash = hashFunction(key);
        while(array[arrayHash] != null){
            if(array[arrayHash].value == key){

                return arrayHash;
            }
            ++arrayHash;
            arrayHash %= koko;
        }
        return -1;
    }
    public void intersect(arrayObject[] array){
        try{
        FileWriter textWriter = new FileWriter(new File("and.txt"));
        ArrayList<String> temp = new ArrayList<>();
        String tempString;
        for(int i = 0; i < array.length; i++){
            if(array[i] != null){
                tempString = "";
                int length = String.valueOf(array[i].value).length();
                if(array[i].foundFromA && array[i].foundFromB){
                    tempString += Integer.toString(array[i].value);

                    for(int a = 0; a < 10-length; a++){
                    tempString += " ";
                    }
                    tempString += Integer.toString(array[i].A_Line);
                    temp.add(tempString);
                }


            }
        }
        Collections.sort(temp, new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
            int n1 = Integer.parseInt(a.split(" ")[0]);
            int n2 = Integer.parseInt(b.split(" ")[0]);

            return n1 - n2;
        }
        });
        for(String s : temp){
            textWriter.write(s);
            textWriter.write(System.lineSeparator());
        }
        textWriter.close();
            System.out.println("Writing intersect file succesful");
        }
        catch(IOException e){
            System.out.println("Failed to save file");
        }
    }

    public arrayObject[] delete(arrayObject[] array, int key){
        int arrayHash = hashFunction(key);
        while(arrayHash < koko){
            if(array[arrayHash] != null && array[arrayHash].value == key){
                array[arrayHash] = null;
                System.out.println("Removal of " + key + " was succesful");
                return array;
            }
            ++arrayHash;

        }
        System.out.println("Removing failed");
        return array;

    }

    public void xor(arrayObject[] array){
        try{

        FileWriter textWriter = new FileWriter(new File("xor.txt"));
        ArrayList<String> temp = new ArrayList<>();
        String tempString;

        for(int i = 0; i < array.length; i++){

            if(array[i] != null){
                tempString ="";
                int hash = hashFunction(i);
                int length = String.valueOf(array[hash].value).length();
                if(array[hash].foundFromA && !array[hash].foundFromB){

                   tempString += Integer.toString(array[hash].value);
                   for(int a = 0; a < 10-length; a++){

                     tempString += " ";
                    }
                    tempString += "1";
                    temp.add(tempString);

                }
                else if(!array[hash].foundFromA && array[hash].foundFromB){

                    tempString += Integer.toString(array[hash].value);

                    for(int a = 0; a < 10-length; a++){
                     tempString += " ";
                    }

                    tempString += "2";
                    temp.add(tempString);
                }

            }
        }
        Collections.sort(temp, new Comparator<String>() {
        @Override
        public int compare(String a, String b) {
            int n1 = Integer.parseInt(a.split(" ")[0]);
            int n2 = Integer.parseInt(b.split(" ")[0]);

            return n1 - n2;
        }
        });
        for(String s : temp){
            textWriter.write(s);
            textWriter.write(System.lineSeparator());
        }
        textWriter.close();
         System.out.println("Writing xor file succesful");
        }
        catch(IOException e){
            System.out.println("Failed to save file");
        }
    }

    public static void main(String[] args) throws IOException{
        HashTable harkka = new HashTable();
        System.out.println("Reading data sets...");
        harkka.getMaxLines("setA.txt","setB.txt");
        arrayObject[] mainArray = new arrayObject[harkka.getMaxSize()];
        Scanner lukija = new Scanner(System.in);
        mainArray = harkka.readFile("setA.txt", mainArray);
        mainArray = harkka.readFile("setB.txt", mainArray);
        System.out.println("There were " + (harkka.koko) + " elements");
        System.out.println("Enter the value you wish to remove, otherwise to proceed, press Enter.");
        String line = lukija.nextLine();
        while(!line.equals("")){
            try{
            mainArray = harkka.delete(mainArray,Integer.parseInt(line));
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a valid Integer");
            }
            System.out.println("Enter another value you wish to remove or press Enter to create text files");
            line = lukija.nextLine();

        }
        harkka.union(mainArray);
        harkka.intersect(mainArray);
        harkka.xor(mainArray);


    }
}
