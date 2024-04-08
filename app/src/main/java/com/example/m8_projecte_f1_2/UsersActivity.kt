package com.example.m8_projecte_f1_2


import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class UsersActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        listView = findViewById(R.id.listView)

        database = FirebaseDatabase.getInstance("https://m8-projecte-f1-2-default-rtdb.europe-west1.firebasedatabase.app/")
        reference = database.reference.child("DATA BASE JUGADORS")

        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usersList = ArrayList<String>()
                for (userSnapshot in dataSnapshot.children) {
                    val username = userSnapshot.child("Nom").value.toString()
                    val score = userSnapshot.child("Puntuacio").value.toString()
                    val imageUrl = userSnapshot.child("Imatge").value.toString() // Suponiendo que has almacenado la URL de la imagen en la base de datos

                    usersList.add("                   Usuari:  $username -- Puntuación:  $score")

                    // Cargar la imagen con Picasso
                    if (imageUrl.isNotEmpty()) {
                        Picasso.get().load(imageUrl).into(object : com.squareup.picasso.Target {
                            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                                // Aquí puedes realizar cualquier acción necesaria cuando la imagen se carga correctamente
                            }

                            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                                // Manejar la falla al cargar la imagen
                            }

                            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                                // Aquí puedes realizar cualquier acción necesaria antes de cargar la imagen
                            }
                        })
                    }
                }

                // Utiliza el adaptador personalizado con el layout list_item.xml
                val adapter = ArrayAdapter(this@UsersActivity, R.layout.list_item, usersList)
                listView.adapter = adapter
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Manejar el error
            }
        })
    }
}
