package com.ku_cs.myfinance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;


public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db = this.getWritableDatabase();

    private static final String dbName = "my_finance.db";
    public static final int dbVersion = 2;

    public DBHelper(@Nullable Context context) {
        super(context, dbName, null, dbVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contacts (c_id integer primary key autoincrement, " +
                "name TEXT, phone TEXT , address Text )");
        db.execSQL("create table list (l_id integer primary key autoincrement, " +
                "c_id integer, Amount INTEGER, title Text, date TEXT, note TEXT)");
        db.execSQL("create table payment (p_id integer primary key autoincrement, " +
                "c_id integer, Amount INTEGER, date TEXT, note TEXT)");

        db.execSQL("CREATE VIEW lists_sum AS SELECT list.c_id, sum(list.Amount) AS total_listed FROM list GROUP by list.c_id");
        db.execSQL("CREATE VIEW payments_sum AS SELECT payment.c_id, sum(payment.Amount) AS total_payments FROM payment GROUP by payment.c_id");

        db.execSQL("CREATE VIEW contacts_detail AS SELECT contacts.c_id, contacts.name, contacts.phone, contacts.address, " +
                " lists_sum.total_listed, payments_sum.total_payments FROM contacts " +
                " LEFT JOIN lists_sum ON contacts.c_id = lists_sum.c_id " +
                " LEFT JOIN payments_sum ON contacts.c_id = payments_sum.c_id");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insertContact(String name, String phone, String address) {
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("phone", phone);
        cv.put("address", address);
        return (db.insert("contacts", null, cv));
    }

    public long insertList(int contact_id, int amount, String title, String date, String note) {
        ContentValues cv = new ContentValues();
        cv.put("c_id", contact_id);
        cv.put("Amount", amount);
        cv.put("title", title);
        cv.put("date", date);
        cv.put("note", note);
        return (db.insert("list", null, cv));
    }

    public Cursor getContacts() {
        return db.rawQuery("SELECT * FROM contacts ", null);
    }

    public Cursor getLists(int l_id) {
        return db.rawQuery("SELECT * FROM list WHERE l_id =" + l_id, null);
    }
    public Cursor getAllListWithName() {
        return db.rawQuery("SELECT contacts.c_id, contacts.name, list.Amount,  list.date, list.title, list.l_id FROM contacts INNER JOIN list ON contacts.c_id = list.c_id", null);
    }
    public Cursor getListVia_contact_id(int contact_id) {
        return db.rawQuery("SELECT contacts.c_id, contacts.name, list.Amount,  list.date, list.title, list.l_id FROM contacts INNER JOIN list ON contacts.c_id = list.c_id WHERE contacts.c_id =" + contact_id, null);
    }
    public Cursor getContactDetail(int contact_id) {
        return db.rawQuery("SELECT * FROM contacts_detail WHERE c_id = " + contact_id, null);
    }
    public Cursor getContactDetail() {
        return db.rawQuery("SELECT * FROM contacts_detail  ", null);
    }


    public Cursor get_lists_count_via_contact_id(int contact_id){
        return db.rawQuery("SELECT list.l_id FROM list WHERE list.c_id = " + contact_id, null);
    }

    public  Cursor get_payments_count_via_contact_id(int contact_id){
        return db.rawQuery("SELECT payment.p_id FROM payment WHERE payment.c_id = " + contact_id, null);
    }
    public void delete_lists_via_contact_id(int contact_id) {
        db.rawQuery("DELETE FROM list WHERE c_id =" + contact_id, null).close();
    }

    public void delete_payments_via_contact_id(int contact_id){
        db.rawQuery("DELETE FROM payment WHERE payment.c_id = "+ contact_id, null).close();
    }
    public long delete_contact(int contact_id) {
        return db.delete("contacts", "c_id=?", new String[]{String.valueOf(contact_id)});
    }
    public long delete_list(int list_id) {
        return db.delete("list", "l_id=?", new String[]{String.valueOf(list_id)});
    }

    public long update_list(int list_id, int amount, String date, String title, String note){
        ContentValues cv = new ContentValues();
        cv.put("Amount", amount);
        cv.put("title", title);
        cv.put("date", date);
        cv.put("note", note);
        return db.update("list", cv,"l_id=?", new String[]{String.valueOf(list_id)});
    }

    public long insertPayment(int contact_id, int amount, String date, String note) {
        ContentValues cv = new ContentValues();
        cv.put("c_id", contact_id);
        cv.put("Amount", amount);
        cv.put("date", date);
        cv.put("note", note);
        return (db.insert("payment", null, cv));
    }

    public Cursor getPayments(int contact_id){
        return db.rawQuery("SELECT contacts.c_id, contacts.name, payment.Amount, payment.date, payment.note, payment.p_id FROM contacts INNER JOIN payment on contacts.c_id = payment.c_id WHERE contacts.c_id =" + contact_id, null);
    }
    public Cursor getPayment(int payment_id){
        return db.rawQuery("SELECT contacts.c_id, contacts.name, payment.Amount, payment.date, payment.note, payment.p_id FROM contacts INNER JOIN payment on contacts.c_id = payment.c_id WHERE payment.p_id =" + payment_id, null);
    }
    public Cursor getPayments(){
        return db.rawQuery("SELECT contacts.c_id, contacts.name, payment.Amount, payment.date, payment.note, payment.p_id FROM contacts INNER JOIN payment on contacts.c_id = payment.c_id" , null);
    }

    public long delete_payment(int payment_id) {
        return db.delete("payment", "p_id=?", new String[]{String.valueOf(payment_id)});
    }
    public long update_payment(int payment_id, int amount, String date, String note){
        ContentValues cv = new ContentValues();
        cv.put("Amount", amount);
        cv.put("date", date);
        cv.put("note", note);
        return db.update("payment", cv,"p_id=?", new String[]{String.valueOf(payment_id)});
    }

}
