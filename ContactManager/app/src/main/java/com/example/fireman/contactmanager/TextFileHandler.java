package com.example.fireman.contactmanager;

import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;

/**
 * Created by Fireman on 2015/11/4.
 */
public class TextFileHandler {

    private Context context;

    public TextFileHandler (Context context) {
        this.context = context;
    }

    public void createContact(Contact contact) {


        try{
            File file = new File("Contacts.txt");
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter(file));
            String row = contact.getfirstName().toString()+","+contact.getlastName().toString()+","+contact.getPhone().toString()+","+contact.getEmail().toString()+"\n";
            bufWriter.write(row);
            bufWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deleteContact(List<Contact> contacts) {
        try{
            File file = new File("Contacts.txt");
            file.delete();
            for(int i = 0; i < contacts.size(); i++){
                createContact(contacts.get(i));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public void editContact(List<Contact> contacts) {
        try{
            File file = new File("Contacts.txt");
            file.delete();
            for(int i = 0; i < contacts.size(); i++){
                createContact(contacts.get(i));
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

    public int getContactCount() {
        int n = 0;
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File("Contacts.txt"));  //I've also tried without the .txt extension
        }catch(Exception e)
        {
            //Send error message.
        }
        if(scanner != null) {
            while (scanner.hasNext()){
                n++;
            }
        }
        return n;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("Contacts.txt"));  //I've also tried without the .txt extension
        }catch(Exception e)
        {
            //Send error message.
        }
        if(scanner != null) {
            while (scanner.hasNext()) {
                String[] line = scanner.nextLine().split(",");
                contacts.add(new Contact(line[0], line[1], line[2], line[3]));
                //Process string here
            }
        }


        return contacts;
    }

}
