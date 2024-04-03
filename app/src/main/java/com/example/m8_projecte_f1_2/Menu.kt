package com.example.m8_projecte_f1_2

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class Menu : AppCompatActivity() {
    //creem unes variables per comprovar ususari i authentificació
    lateinit var auth: FirebaseAuth
    lateinit var tancarSessio: Button
    lateinit var CreditsBtn: Button
    lateinit var PuntuacionsBtn: Button
    lateinit var jugarBtn: Button
    lateinit var editarBtn: Button


    lateinit var miPuntuaciotxt: TextView
    lateinit var puntuacio: TextView
    lateinit var uid: TextView
    lateinit var correo: TextView
    lateinit var nom: TextView
    lateinit var edat: TextView
    lateinit var poblacio: TextView

    lateinit var imatgePerfil: ImageView


    //reference serà el punter que ens envia a la base de dades de jugadors
    lateinit var reference: DatabaseReference


    var user: FirebaseUser? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth= FirebaseAuth.getInstance()
        user =auth.currentUser
        tancarSessio =findViewById<Button>(R.id.tancarSessio)
        tancarSessio.setOnClickListener(){
            tancalaSessio()
        }

        tancarSessio =findViewById<Button>(R.id.tancarSessio)
        CreditsBtn =findViewById<Button>(R.id.CreditsBtn)
        PuntuacionsBtn =findViewById<Button>(R.id.PuntuacionsBtn)
        jugarBtn =findViewById<Button>(R.id.jugarBtn)


        CreditsBtn.setOnClickListener(){
            Toast.makeText(this,"Credits", Toast.LENGTH_SHORT).show()
        }
        PuntuacionsBtn.setOnClickListener(){
            Toast.makeText(this,"Puntuacions", Toast.LENGTH_SHORT).show()
        }
        jugarBtn.setOnClickListener(){
            Toast.makeText(this,"JUGAR", Toast.LENGTH_SHORT).show()
            val intent= Intent(this, SeleccioNivel::class.java)
            startActivity(intent)
            //finish()
        }

        //Aquí creem un tipus de lletra a partir de una font
        val tf = Typeface.createFromAsset(assets,"fonts/DejaVuSans.ttf")

        miPuntuaciotxt=findViewById(R.id.miPuntuaciotxt)

        puntuacio=findViewById(R.id.puntuacio)
        uid=findViewById(R.id.uid)
        correo=findViewById(R.id.correo)
        nom=findViewById(R.id.nom)

        //els hi assignem el tipus de lletra
        miPuntuaciotxt.setTypeface(tf)
        puntuacio.setTypeface(tf)
        uid.setTypeface(tf)
        correo.setTypeface(tf)
        nom.setTypeface(tf)

        //fem el mateix amb el text dels botons
        tancarSessio.setTypeface(tf)
        CreditsBtn.setTypeface(tf)
        PuntuacionsBtn.setTypeface(tf)
        jugarBtn.setTypeface(tf)

        editarBtn = findViewById<Button>(R.id.editarBtn)


        edat=findViewById(R.id.edat)
        poblacio=findViewById(R.id.poblacio)
        imatgePerfil=findViewById(R.id.imatgePerfil)
        //Assignem tipus de lletra al botó
        editarBtn.setTypeface(tf)
        editarBtn.setOnClickListener() {
            Toast.makeText(this, "EDITAR", Toast.LENGTH_SHORT).show()
            canviaLaImatge()
        }

    }
    private fun tancalaSessio() {
        auth.signOut() //tanca la sessió
        //va a la pantalla inicial
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    // Aquest mètode s'executarà quan s'obri el minijoc

    override fun onStart() {
        usuariLogejat()
        super.onStart()
    }
    private fun  usuariLogejat()
    {
        if (user !=null)
        {
            Toast.makeText(this,"Jugador logejat",
                Toast.LENGTH_SHORT).show()
            consulta()
        }
        else
        {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            //finish()
        }
    }

    private fun consulta(){
        //Amb Firebase no fem consultes realment, el que fem en connectar-nos a una referencia
                // i aquesta anirà canviant automàticament quan canvii la base de dades
        // reference apunta a "DATA BASE JUGADORS"
        // sempre es crea un referencia a un punt del arbre de la base de dades
        // Per exemple si tenim la base de dades
        // arrel
        // - nivell dos
        // - nivell dos.1
        // - nom: "pepe"
        // - dni: "1231212121"
        // - nivell dos.2: "34"
        // - nivell dos.3: "36"
        //var bdasereference:DatabaseReference = FirebaseDatabase.getInstance().getReference()
        // .child("nivell dos")
        // .child("nivell dos.1")
        // Ara estariem al novell dos.1 del arbre
        // Ens podem subscriure amb un listener que té dos métodes
        // onDataChange (es crida si s'actualitza les dades o és la primera vegada que ens suscribim)
        // onCancelled Es crida si hi ha un error o es cancel·la la lectura
        // al primer métode rebrem un objecte json que és la branca sobre la que demanem actualització
                // getkey retorna la clave del objecte
                // getValue retorna el valor
                // els subnodes (fills) es recuperen amb getChildren
                // es poden llegir com un llistat d'objectes Datasnapshots
        // o navegar a subnodes concrets amb child("nomdelsubnode")
        var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://m8-projecte-f1-2-default-rtdb.europe-west1.firebasedatabase.app/")
        var bdreference:DatabaseReference = database.getReference("DATA BASE JUGADORS")
        Toast.makeText(this, "eeeeeeeeeeeeeeeeeeeeeeeeee", Toast.LENGTH_LONG).show()
        bdreference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i ("pepe","arrel value"+
                        snapshot.getValue().toString())
                Log.i ("pepe","arrel key"+ snapshot.key.toString())
                // ara capturem tots els fills
                var trobat:Boolean = false
                for (ds in snapshot.getChildren()) {
                    Log.i ("pepe","DS key:"+ds.child("Uid").key.toString())
                    Log.i ("pepe","DS value:"+ds.child("Uid").getValue().toString())
                    Log.i ("pepe","DS data:"+ds.child("Data").getValue().toString())
                    Log.i ("pepe","DS mail:"+ds.child("Email").getValue().toString())
                    //mirem si el mail és el mateix que el del jugador
                    //si és així, mostrem les dades als textview corresponents

                    if (ds.child("Email").getValue().toString().equals(user?.email)){
                        trobat=true

                        //carrega els textview
                        puntuacio.setText(ds.child("Puntuacio").getValue().toString())
                        uid.setText(ds.child("Uid").getValue().toString())
                        correo.setText(ds.child("Email").getValue().toString())
                        nom.setText(ds.child("Nom").getValue().toString())
                        poblacio.setText( ds.child("Poblacio").getValue().toString())
                        edat.setText( ds.child("Edat").getValue().toString())


                        try {
                            val imatge: String = ds.child("Imatge").getValue().toString()
                            Picasso.get().load(imatge).into(imatgePerfil);
                        } catch (e:Exception){
                            Picasso.get().load(R.drawable.carlos).into(imatgePerfil)
                        }

                    }
                    if (!trobat)
                    {
                    Log.e ("ERROR","ERROR NO TROBAT MAIL")
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Log.e ("ERROR","ERROR DATABASE CANCEL")
            }
        })
    }
    //----------------------------------------Permisos----------------
    fun isPermissionsAllowed(): Boolean {
        return if
                       (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            false
        } else true
    }
    fun askForPermissions(): Boolean {
        val REQUEST_CODE=201
        if (!isPermissionsAllowed()) {
            if
                    (ActivityCompat.shouldShowRequestPermissionRationale(this ,android.Manifest.permission.READ_MEDIA_IMAGES)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(this
                    ,arrayOf(android.Manifest.permission.READ_MEDIA_IMAGES),REQUEST_CODE)
            }
            return false
        }
        return true
    }
    override fun onRequestPermissionsResult(requestCode:
                                            Int,permissions: Array<String>,grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions,
            grantResults)
        val REQUEST_CODE=201
        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    // permission is granted, you can perform your operation here
                } else {
                    // permission is denied, you can ask for permission again, if you want
                    // askForPermissions()
                }
                return
            }
        }
    }
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
                .setPositiveButton("App Settings", DialogInterface.OnClickListener { dialogInterface, i ->
                        // send to app settings if permission is denied permanently
                        val intent = Intent()
                        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                        val uri = Uri.fromParts("package",
                            getPackageName(), null)
                        intent.data = uri
                        startActivity(intent)
                    })
                .setNegativeButton("Cancel",null)
                .show()
    }
//----------------------------------------------------------------

    private fun canviaLaImatge() {
        //utilitzarem un alertdialog que seleccionara de galeria o agafar una foto
                // Si volem fer un AlertDialog amb més de dos elements (amb una llista),
        // Aixó ho fariem amb fragments (que veurem més endevant)
        // Aquí hi ha un tutorial per veure com es fa:
        // https://www.codevscolor.com/android-kotlin-list-alert-dialog
        //Veiem com es crea un de dues opcions (habitualment acceptar o cancel·lar:
        val dialog = AlertDialog.Builder(this)
            .setTitle("CANVIAR IMATGE")
            .setMessage("Seleccionar imatge de: ")
            .setNegativeButton("Galeria") { view, _ ->
                Toast.makeText(this, "De galeria",
                    Toast.LENGTH_SHORT).show()
                //mirem primer si tenim permisos per a accedir a Read External Storage
                        if (askForPermissions()) {
                            // Permissions are already granted, do your stuff
                            // Aquí agafarem de la galeria la foto que ens calgui
                        }
                        else{
                            Toast.makeText(this, "ERROR PERMISOS",
                                Toast.LENGTH_SHORT).show()
                        }
            }
            .setPositiveButton("Càmera") { view, _ ->
                Toast.makeText(this, "A IMPLEMENTAR PELS ALUMNES",
                    Toast.LENGTH_LONG).show()
                view.dismiss()
            }
            .setCancelable(false)
            .create()
        dialog.show()
    }

}