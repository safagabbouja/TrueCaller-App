package com.example.gestioncontact;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOM = "nom";
    private static final String COLUMN_PSEUDO = "pseudo";
    private static final String COLUMN_NUMERO = "numero";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NOM + " TEXT,"
                + COLUMN_PSEUDO + " TEXT,"
                + COLUMN_NUMERO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM, contact.getNom());
        values.put(COLUMN_PSEUDO, contact.getPseudo());
        values.put(COLUMN_NUMERO, contact.getNumero());
        long id = db.insert(TABLE_CONTACTS, null, values);
        contact.setId((int) id);
        db.close();
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_ID);
                int nomIndex = cursor.getColumnIndex(COLUMN_NOM);
                int pseudoIndex = cursor.getColumnIndex(COLUMN_PSEUDO);
                int numeroIndex = cursor.getColumnIndex(COLUMN_NUMERO);

                if (idIndex >= 0 && nomIndex >= 0 && pseudoIndex >= 0 && numeroIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String nom = cursor.getString(nomIndex);
                    String pseudo = cursor.getString(pseudoIndex);
                    String numero = cursor.getString(numeroIndex);
                    contactList.add(new Contact(id, nom, pseudo, numero));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOM, contact.getNom());
        values.put(COLUMN_PSEUDO, contact.getPseudo());
        values.put(COLUMN_NUMERO, contact.getNumero());

        db.update(TABLE_CONTACTS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, COLUMN_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }
}
