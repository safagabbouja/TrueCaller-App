package com.example.gestioncontact;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyContactRecyclerAdaptor extends RecyclerView.Adapter<MyContactRecyclerAdaptor.MyViewHolder> {

    Context context;
    ArrayList<Contact> contacts;
    private DatabaseHelper databaseHelper;

    public MyContactRecyclerAdaptor(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
        databaseHelper = new DatabaseHelper(context);  // Initialize DatabaseHelper
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.view_contact, parent, false);  // Inflate the contact view layout
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        holder.tvnom_contact.setText(contact.getNom());
        holder.tvpseudo_contact.setText(contact.getPseudo());
        holder.tvnumero_contact.setText(contact.getNumero());

        // Handle delete, edit, and call actions
        holder.deleteContact.setOnClickListener(v -> removeContact(position));
        holder.editContact.setOnClickListener(v -> editContact(contact));
        holder.callContact.setOnClickListener(v -> callContact(contact.getNumero()));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvnom_contact, tvpseudo_contact, tvnumero_contact;
        ImageView deleteContact, editContact, callContact;

        public MyViewHolder(@NonNull View v) {
            super(v);
            tvnom_contact = v.findViewById(R.id.tvnom_contact);
            tvpseudo_contact = v.findViewById(R.id.tvpseudo_contact);
            tvnumero_contact = v.findViewById(R.id.tvnumero_contact);
            deleteContact = v.findViewById(R.id.ivdel_contact);
            editContact = v.findViewById(R.id.ivedit_contact);
            callContact = v.findViewById(R.id.tvcall_contact);
        }
    }

    // Method to update the list of contacts in the adapter
    public void filterList(ArrayList<Contact> filteredContacts) {
        this.contacts = filteredContacts;
        notifyDataSetChanged();  // Refresh the RecyclerView
    }

    // Remove contact from the database and the list
    private void removeContact(int position) {
        Contact contact = contacts.get(position);
        new AlertDialog.Builder(context)
                .setTitle("Confirm Deletion")
                .setMessage("Are you sure you want to delete this contact?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    databaseHelper.deleteContact(contact);  // Remove from DB
                    contacts.remove(position);  // Remove from the list
                    notifyDataSetChanged();  // Refresh the RecyclerView
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    // Edit contact in the database
    private void editContact(Contact contact) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle("Edit Contact");

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.view_dialog, null, false);
        alertBuilder.setView(v);

        // Set up dialog view with existing contact info
        EditText editNom = v.findViewById(R.id.editNom);
        EditText editPseudo = v.findViewById(R.id.editPseudo);
        EditText editNumero = v.findViewById(R.id.editNumero);

        editNom.setText(contact.getNom());
        editPseudo.setText(contact.getPseudo());
        editNumero.setText(contact.getNumero());

        // Handle save button click
        alertBuilder.setPositiveButton("Save", (dialog, which) -> {
            contact.setNom(editNom.getText().toString());
            contact.setPseudo(editPseudo.getText().toString());
            contact.setNumero(editNumero.getText().toString());

            databaseHelper.updateContact(contact);  // Update the DB
            notifyDataSetChanged();  // Update the RecyclerView
        });

        alertBuilder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        alertBuilder.show();
    }

    // Handle calling the contact
    /*private void callContact(String numero) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + numero));
        context.startActivity(intent);
    }*/
    // Handle calling the contact directly
    private void callContact(String numero) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + numero));

        // Ensure that permission to call has been granted before making the call
        if (context.checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            context.startActivity(intent);
        } else {
            // If permission is not granted, request permission (you should handle permission request in the activity)
            Toast.makeText(context, "Permission to call is not granted.", Toast.LENGTH_SHORT).show();
        }
    }

}
