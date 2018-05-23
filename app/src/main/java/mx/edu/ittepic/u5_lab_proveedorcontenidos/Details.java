package mx.edu.ittepic.u5_lab_proveedorcontenidos;

import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class Details extends AppCompatActivity {

    EditText tel,nom;
    String n="",t="";
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        Bundle datos = this.getIntent().getExtras();


        if(datos!=null){
            id = datos.getInt("id");
            n = datos.getString("nom");
            t = datos.getString("tel");
        }

        nom=(EditText)findViewById(R.id.nom1);
        tel=(EditText)findViewById(R.id.tel1);
        nom.setText(n);
        tel.setText(t);
        final Intent in= new Intent(this,MainActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    updateContact(id+"",tel.getText().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                }
                startActivity(in);
            }
        });
    }


    public void updateContact(String id,String nombre) throws RemoteException, OperationApplicationException {
        ArrayList<ContentProviderOperation> ops =
                new ArrayList<ContentProviderOperation>();

        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(ContactsContract.Data._ID + "= ?", new String[]{id})
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, nombre)
                .build()
        );
        getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
    }
}

