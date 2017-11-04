package com.jongewaard.udemy.curso.seccion_01;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private EditText editTextContact;
    private EditText editTextEmail;
    private EditText editTextEmail_2;

    private ImageButton imageButtonPhone;
    private ImageButton imageButtonWeb;
    private ImageButton imageButtonContact;
    private ImageButton imageButtonEmail;
    private ImageButton imageButtonPhone_2;
    private ImageButton imageButtonEmail_2;
    private ImageButton imageButtonCamera;


    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Activar flecha ir hacia atrás
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        editTextContact = (EditText) findViewById(R.id.editTextContact);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextEmail_2 = (EditText) findViewById(R.id.editTextEmail_2);

        imageButtonPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imageButtonWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imageButtonContact = (ImageButton) findViewById(R.id.imageButtonContact);
        imageButtonEmail = (ImageButton) findViewById(R.id.imageButtonEmail);
        imageButtonCamera = (ImageButton) findViewById(R.id.imageButtonCamera);
        imageButtonPhone_2 = (ImageButton) findViewById(R.id.imageButtonPhone_2);
        imageButtonEmail_2 = (ImageButton) findViewById(R.id.imageButtonEmail_2);

        //*********** Botón para la llamada - Comienzo **************
        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumber = editTextPhone.getText().toString();

                if (phoneNumber != null) {
                    //Comprobar versión actual de Android que estamos corriendo. "Oreo"
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //Comprobar si ha aceptado o NO ha haceptado o nunca se  le ha preguntado.
                        if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                            //Ha aceptado!
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;

                            startActivity(i);

                        }else{
                            //Ha denegado o no se le ha preguntado aún.
                            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                //No se le ha preguntado aún. - Si es mayor o igual a la version que tengo. llamo al metodo NewVersion
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);

                            }else{
                                //Ha denegado
                                Toast.makeText(ThirdActivity.this, "Please, enable the permission.", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:"+getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);

                            }
                        }
                    } else {
                        OlderVersions(phoneNumber);
                    }
                }
                else{
                    Toast.makeText(ThirdActivity.this, "Insert a phone number.", Toast.LENGTH_SHORT).show();
                }
            }

    private void OlderVersions(String phoneNumber) {
        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));

        if(CheckPermission(Manifest.permission.CALL_PHONE)){
            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                startActivity(intentCall);
            } else {
                Toast.makeText(ThirdActivity.this, "You declined the access", Toast.LENGTH_SHORT).show();
            }
    }
        });

        //*********** Botón para la llamada - Final **************

        //*********** Botón para la dirección Web - Comienzo **************
        imageButtonWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextWeb.getText().toString();
                //que la dirección Web no sea nula y que no este en blanco
                if(url != null && !url.isEmpty()){
                    //Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+url));
                    //otra forma de hacer el (Intent) codigo de arriba
                    Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://"+url));
                    startActivity(intentWeb);
                } else{
                Toast.makeText(ThirdActivity.this, "Please, EditText can not go empty..", Toast.LENGTH_SHORT).show();
            }
            }
        });

        imageButtonContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextContact.getText().toString();
                if(url != null && !url.isEmpty()){
                    Intent intentContacts = new Intent();
                    intentContacts.setAction(Intent.ACTION_VIEW);
                    intentContacts.setData(Uri.parse("content://contacts/people"));
                    startActivity(intentContacts);
                }else{
                    Toast.makeText(ThirdActivity.this, "Please, EditText can not go empty..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Email rápido.
        imageButtonEmail.setOnClickListener(new View.OnClickListener() {
            String addresses = "eltunolio@hotmail.com";
            String subject = "Desde Nîmes con love...";
            String text = "Hi there, I love my form app, but... ";
            //String attachment = "viva la chota";

            @Override
            public void onClick(View v) {
                String url = editTextEmail.getText().toString();
                if(url != null && !url.isEmpty()){

                    //Email rápido.
                    Intent intentMailTo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + addresses));
                    startActivity(intentMailTo);
                }else{
                    Toast.makeText(ThirdActivity.this, "Please, EditText can not go empty..", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Telefono 2 sin permisos requeridos!
        imageButtonPhone_2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:666111222"));
                startActivity(intentPhone);

            }
        });

        //Email Completo.
        imageButtonEmail_2.setOnClickListener(new View.OnClickListener() {
            String addresses = "eltunolio@hotmail.com";
            String subject = "Mail's title";
            String text = "Hi there, I love my app, but... ";
            //String attachment = "viva la chota";
            @Override
            public void onClick(View v) {
                String url = editTextEmail_2.getText().toString();
                if(url != null && !url.isEmpty()){
                    //Email Completo.
                    Intent intentMailTo = new Intent(Intent.ACTION_SEND, Uri.parse(addresses));
                    //intentMailTo.setData(Uri.parse(addresses));
                    //intentMailTo.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    intentMailTo.setType("plain/text");
                    //intentMailTo.setData(Uri.parse("mailto:" + addresses)); // only email apps should handle this
                    intentMailTo.putExtra(Intent.EXTRA_SUBJECT, subject);
                    //intentMailTo.putExtra(Intent.EXTRA_EMAIL, addresses);
                    //intentMailTo.setType("message/rfc822");
                    intentMailTo.setType("plain/text");
                    intentMailTo.putExtra(Intent.EXTRA_TEXT, text);
                    intentMailTo.putExtra(Intent.EXTRA_EMAIL, new String[]{"german@gmail.com", "juanico@hotmail.com", "geogy@yahoo.com"});
                    startActivity(Intent.createChooser(intentMailTo, "Elige el cliente de correo."));
                    //startActivity(intentMailTo);
                }else{
                    Toast.makeText(ThirdActivity.this, "Please, EditText can not go empty..", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Abrir Cámara!
        imageButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                //startActivity(intentCamera);
                startActivityForResult(intentCamera, PICTURE_FROM_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case PICTURE_FROM_CAMERA:

                if(resultCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "There was an error with the picture, try agian.", Toast.LENGTH_LONG).show();
                }

                break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            //Estamos en el caso del telefono
            switch (requestCode){
                case PHONE_CALL_CODE:

                    String permission = permissions[0];
                    int result = grantResults[0];

                    if(permission.equals(Manifest.permission.CALL_PHONE)){

                        //Comprobar si ha sido aceptado o denegado la petición de permiso
                        if(result == PackageManager.PERMISSION_GRANTED){

                            //Concedió su permiso
                            String phoneNumber = editTextPhone.getText().toString();
                            Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            if(ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;

                            startActivity(intentCall);

                        }else{
                            //No concedió su permiso.
                            Toast.makeText(this, "You declined the access", Toast.LENGTH_SHORT).show();
                        }
                    }break;
                default:
                     super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                     break;
            }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /*
        Este metodo, va a comprobar si tenemos ese permiso, solo comprueba
        si lo tenemos en el manifest y si to-do va bien
        tengo que agregar en el manifest este permiso!!!!
        <uses-permission android:name="android.permission.CALL_PHONE" /> */
    private boolean CheckPermission(String permission){

        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}

