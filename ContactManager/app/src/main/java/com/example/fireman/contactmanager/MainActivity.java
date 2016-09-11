package com.example.fireman.contactmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static final int EDIT = 0, DELETE = 1;

    EditText firstnameTxt, lastnameTxt, phoneTxt, emailTxt;
    List<Contact> Contacts = new ArrayList<Contact>();
    ListView contactListView;
    ArrayAdapter<Contact> contactAdapter;
    int longClickedItemIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstnameTxt = (EditText) findViewById(R.id.txtFirstName);
        lastnameTxt = (EditText) findViewById(R.id.txtLastName);
        phoneTxt = (EditText) findViewById(R.id.txtPhoneNumber);
        emailTxt = (EditText) findViewById(R.id.txtEmailAddress);
        contactListView = (ListView) findViewById(R.id.listView);
        //tfHandler = new TextFileHandler();

        registerForContextMenu(contactListView);

        contactListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                longClickedItemIndex = position;
                return false;
            }
        });
        final TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("creator");
        tabSpec.setContent(R.id.tabCreator);
        tabSpec.setIndicator("Creator");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("list");
        tabSpec.setContent(R.id.tabContactList);
        tabSpec.setIndicator("LIST");
        tabHost.addTab(tabSpec);

        final Button addBtn = (Button) findViewById(R.id.btnAdd);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Contact contact = new Contact(firstnameTxt.getText().toString(), lastnameTxt.getText().toString(), phoneTxt.getText().toString(), emailTxt.getText().toString());
                //tfHandler.createContact(contact);


                createContact(contact);



                try {
                    String inputMessage;
                    FileInputStream fileInputStream = openFileInput("Contacts.txt");
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuffer stringBuffer = new StringBuffer();
                    while ((inputMessage = bufferedReader.readLine())!= null){
                        stringBuffer.append(inputMessage + "\n");
                    }

                    Toast.makeText(getApplicationContext(),  firstnameTxt.getText().toString() + " " + lastnameTxt.getText().toString() +  " has been added to Contact list!", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                addContact(firstnameTxt.getText().toString(), lastnameTxt.getText().toString(), phoneTxt.getText().toString(), emailTxt.getText().toString());
                Collections.sort(Contacts, Contact.ConNameComparator);
                populateList();
                contactAdapter.notifyDataSetChanged();
                tabHost.setCurrentTab(1);
                //Toast.makeText(getApplicationContext(), tabHost.getCurrentTab() + " " + tabHost.getCurrentTabTag() + " has been added to Contact list!", Toast.LENGTH_SHORT).show();
            }
        });




        firstnameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addBtn.setEnabled(!firstnameTxt.getText().toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        if (getContactCount() != 0)
            Contacts.addAll(getAllContacts());

        Collections.sort(Contacts, Contact.ConNameComparator);

         populateList();
    }

    public int getContactCount() {
        int n = 0;

        try {
            FileInputStream fileInputStream = openFileInput("Contacts.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (bufferedReader.readLine()!= null){
                n++;
            }

            //Toast.makeText(getApplicationContext(),  n +  " has been added to Contact list!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return n;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<Contact>();

        try {
            String inputMessage;
            FileInputStream fileInputStream = openFileInput("Contacts.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((inputMessage = bufferedReader.readLine())!= null){
                String[] line = inputMessage.split(",");
                contacts.add(new Contact(line[0], line[1], line[2], line[3]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return contacts;
    }

    public void createContact(Contact contact) {


        String file_name = "Contacts.txt";
        String Message = contact.getfirstName().toString()+","+contact.getlastName().toString()+","+contact.getPhone().toString()+","+contact.getEmail().toString()+"\n";
        try {
            FileOutputStream fileOutputStream = openFileOutput(file_name, MODE_PRIVATE | MODE_APPEND);
            fileOutputStream.write(Message.getBytes());
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateContact(List<Contact> contacts) {


        try{
            StringBuffer stringBuffer = new StringBuffer();
            for(int i = 0; i < contacts.size(); i++){
                stringBuffer.append(contacts.get(i).getfirstName().toString()+ "," + contacts.get(i).getlastName().toString() + "," + contacts.get(i).getPhone().toString()+ "," + contacts.get(i).getEmail().toString()+"\n");
            }
            FileOutputStream fileOutputStream = openFileOutput("Contacts.txt", MODE_PRIVATE);
            fileOutputStream.write(stringBuffer.toString().getBytes());
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);

        menu.setHeaderIcon(R.drawable.pencil_icon);
        menu.setHeaderTitle("Contact Options");
        menu.add(Menu.NONE, EDIT, menu.NONE, "Edit Contact");
        menu.add(Menu.NONE, DELETE, menu.NONE, "Delete Contact");
        Contact currentContact = Contacts.get(longClickedItemIndex);

    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case EDIT:
                // TODO: Implement editing a contact
                Contact currentContact = Contacts.get(longClickedItemIndex);
                String firstName = currentContact.getfirstName();
                String lastName = currentContact.getlastName();
                String phoneNumber = currentContact.getPhone();
                String emailAddress = currentContact.getEmail();
                Intent i = new Intent(this, Main2Activity.class);
                i.putExtra("Firstname", firstName);
                i.putExtra("Lastname", lastName);
                i.putExtra("Phonenumber", phoneNumber);
                i.putExtra("Emailaddress", emailAddress);
                startActivityForResult(i, 2);


                break;
            case DELETE:
                Contacts.remove(longClickedItemIndex);
                updateContact(Contacts);
                contactAdapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String firstname= data.getStringExtra("Firstname");
            String lastname= data.getStringExtra("Lastname");
            String phonenumber= data.getStringExtra("Phonenumber");
            String emailaddress = data.getStringExtra("Emailaddress");
            editContact(firstname, lastname, phonenumber, emailaddress, longClickedItemIndex);
            Collections.sort(Contacts, Contact.ConNameComparator);
            updateContact(Contacts);
            populateList();
            contactAdapter.notifyDataSetChanged();
            //Toast.makeText(getApplicationContext(), message  +  "  has been added to Contact list!", Toast.LENGTH_SHORT).show();
        }
    }

    private void populateList() {
        contactAdapter = new ContactListAdapter();
        contactListView.setAdapter(contactAdapter);
    }

    private  void addContact(String firstname, String lastnamem, String phone, String email) {
        Contacts.add(new Contact(firstname, lastnamem, phone, email));
    }

    private void editContact(String firstname, String lastname, String phone, String email, int id) {
        Contact currentContact = new Contact(firstname, lastname, phone, email);
        Contacts.set(id, currentContact);
        //tfHandler.editContact(Contacts);
    }

    private class ContactListAdapter extends ArrayAdapter<Contact> {
        public ContactListAdapter() {
            super (MainActivity.this, R.layout.listview_item, Contacts);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.listview_item, parent, false);

            Contact currentContact = Contacts.get(position);

            TextView name = (TextView) view.findViewById(R.id.contactName);
            name.setText(currentContact.getfirstName());
            name.append(" ");
            name.append(currentContact.getlastName());
            TextView phone = (TextView) view.findViewById(R.id.phoneNumber);
            phone.setText(currentContact.getPhone());
            TextView email = (TextView) view.findViewById(R.id.emailAddress);
            email.setText(currentContact.getEmail());

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
