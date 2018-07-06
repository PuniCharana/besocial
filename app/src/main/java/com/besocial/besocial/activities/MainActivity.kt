package com.besocial.besocial.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.besocial.besocial.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        // NOTE: this is not a back button, instead profile button
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_person_24dp)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fab.setOnClickListener { view ->
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        return when (item.itemId) {
            // NOTE: this is not a back button, instead profile button
            android.R.id.home -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
