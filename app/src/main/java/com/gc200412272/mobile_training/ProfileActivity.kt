package com.gc200412272.mobile_training

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.toolbar_main.*
import java.io.File

class ProfileActivity : AppCompatActivity() {

    // FirebaseAuth instance
    private val authDb = FirebaseAuth.getInstance()

    // vars for profile photo
    private val REQUEST_CODE = 1000 // this is constant for the take picture intent in android
    private lateinit var filePhoto: File
    private val FILE_NAME = "photo.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        // populate the textviews based on the current user's profile
        if (authDb.currentUser != null) {
            nameTextView.text = authDb.currentUser!!.displayName
            emailTextView.text = authDb.currentUser!!.email

//            // display photoUri if any
//            var profilePhoto: Uri? = authDb.currentUser!!.photoUrl
//            if (profilePhoto != null) {
//                profilePhoto.path?.let {
//                    loadProfileImage(it)
//                }
//            }
        } else {
            logout()
        }

        profileLogoutFab.setOnClickListener {
            logout()
        }

        // enable scrolling on the Terms textview
        termsTextView.movementMethod = ScrollingMovementMethod()

        //instantiate toolbar
        setSupportActionBar(topToolbar)
    }

    // 2 overrides to display menu and handle its action
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        // inflate the main menu to add the items to the toolbar
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // navigate based on which menu item was clicked
        when (item.itemId) {
            R.id.action_add -> {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                return true
            }
            R.id.action_list -> {
                startActivity(Intent(applicationContext, ListActivity::class.java))
                return true
            }
            R.id.action_profile -> {
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        authDb.signOut()
        finish()
        startActivity(Intent(this, SigninActivity::class.java))
    }
}