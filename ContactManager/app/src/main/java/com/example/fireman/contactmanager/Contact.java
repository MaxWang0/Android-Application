package com.example.fireman.contactmanager;

import java.util.Comparator;


/**
 * Created by Fireman on 2015/10/31.
 */
public class Contact {

    private String _firstname, _lastname, _phone, _email;
    //private Uri _imageURI;
    private int _id;

    public Contact (String firstname, String lastnamem, String phone, String email) {
        _firstname = firstname;
        _lastname = lastnamem;
        _phone = phone;
        _email = email;
    }



    public static Comparator<Contact> ConNameComparator = new Comparator<Contact>() {

        public int compare(Contact c1, Contact c2) {
            String firstName1 = c1.getfirstName().toUpperCase();
            String firstName2 = c2.getfirstName().toUpperCase();

            //ascending order
            return firstName1.compareTo(firstName2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};



    public String getfirstName() {
        return _firstname;
    }

    public String getlastName() {
        return _lastname;
    }

    public String getPhone() {
        return _phone;
    }

    public String getEmail() {
        return _email;
    }


    //public Uri getImageURI() { return _imageURI; }
}
