package com.example.dialogusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {


    private lateinit var edtEmail: EditText
    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide() //Hides Action Bar

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtUsername = findViewById(R.id.edt_username)
        edtPassword = findViewById(R.id.edt_password)
        btnSignUp = findViewById(R.id.btnSignup)

        btnSignUp.setOnClickListener {

            val email = edtEmail.text.toString()
            val name = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            signUp(email, name,password);
        }

    }

    private fun signUp(email:String, name:String, password:String){
        //logic for signing up user
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success

                    addUserToDatabase(email, name, mAuth.currentUser?.uid!!)//Null-safe uid !
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun addUserToDatabase(email:String, name:String, uid:String){

        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("User").child(uid).setValue(User(email, name, uid))

    }



}