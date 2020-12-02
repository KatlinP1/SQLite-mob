package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.sqlite.database.DatabaseHelper;
import com.example.sqlite.model.Code;
import com.example.sqlite.model.Contact;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class ContactActivity extends AppCompatActivity {

    //declairing the variables
    private Code code= null;
    private Contact contact = null;
    private EditText etFirst, etLast, etAddress, etPhone, etEmail, etDate, etCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        //adding back arrow button on actionbar + add override for onSupportNavigateUp method
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //initializing variable
        etFirst = findViewById(R.id.etFirstContact);
        etLast= findViewById(R.id.etLastContact);
        etAddress= findViewById(R.id.etAddressContact);
        etPhone= findViewById(R.id.etPhoneContact);
        etEmail= findViewById(R.id.etEmailContact);
        etDate= findViewById(R.id.etDateContact);
        etCode= findViewById(R.id.etCodeContact);
        //want the focus to be on first field when action opens
        etFirst.requestFocus();

        //getting code from mainactiviy for contact
        Intent intentCode= getIntent();
        Object object= intentCode.getSerializableExtra("code");
        if(object !=null){
            code= (Code) object;
            Calendar calendar =Calendar.getInstance();
            etDate.setText(String.format(Locale.getDefault(), "%02d-%02d-%d",
                    calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.YEAR)));
        }else {
            contact =(Contact) intentCode.getSerializableExtra("contact");
            if (contact != null){
                etFirst.setText(contact.getFirst());
                etLast.setText(contact.getLast());
                etAddress.setText(contact.getAddress());
                etPhone.setText(contact.getPhone());
                etEmail.setText(contact.getEmail());
                etDate.setText(contact.getDates());
                code=contact.getCode();
            }
        }
        etCode.setText(code.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onAdd(View view) {
        String first = etFirst.getText().toString().trim();
        if (first.length() >0 ){
            String last= etLast.getText().toString().trim();
            String address= etAddress.getText().toString().trim();
            String phone= etPhone.getText().toString().trim();
            String email= etEmail.getText().toString().trim();
            String date= etDate.getText().toString().trim();

            DatabaseHelper databaseHelper= new DatabaseHelper(this);
            SQLiteDatabase sqLiteDatabase= databaseHelper.getWritableDatabase();

            ContentValues values= new ContentValues();
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.FIRST_COLUMN],first);
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.LAST_COLUMN], last);
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.ADDRESS_COLUMN],address);
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.CODE_CONTACT_COLUMN], code.getCode());
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.PHONE_COLUMN], phone);
            values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.EMAIL_COLUMN], email);

            if (contact==null){
                values.put(DatabaseHelper.CONTACTS_COLUMNS[DatabaseHelper.DATE_COLUMN], date);
                sqLiteDatabase.insert(DatabaseHelper.CONTACTS_TABLE, null,values);
            } else {
                Calendar calendar =Calendar.getInstance();
                etDate.setText(String.format(Locale.getDefault(), "%02d-%02d-%d",
                        calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH) +1, calendar.get(Calendar.YEAR)));
                String[] params = {""+contact.getId()};
                sqLiteDatabase.update(DatabaseHelper.CONTACTS_TABLE,values, "id=?", params);

            }

            sqLiteDatabase.close(); //andmebaasi läheb kinni
            onSupportNavigateUp();
        } else {
            etFirst.setError(getResources().getString(R.string.inputEntry)); //This fields needs to be filled
            etFirst.requestFocus();
        }
    }

    public void onDelete(View view) {
        if (contact != null) {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
            String[] params= { "" + contact.getId()};
            sqLiteDatabase.delete(DatabaseHelper.CONTACTS_TABLE,"id= ?", params);
            sqLiteDatabase.close();
            onSupportNavigateUp();
        }

    }
}