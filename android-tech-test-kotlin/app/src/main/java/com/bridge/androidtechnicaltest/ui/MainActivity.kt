package com.bridge.androidtechnicaltest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bridge.androidtechnicaltest.R
import com.bridge.androidtechnicaltest.ui.pupil.CreatePupilFragment
import com.bridge.androidtechnicaltest.ui.pupil.DeletePupilFragment
import com.bridge.androidtechnicaltest.ui.pupil.PupilDetailFragment
import com.bridge.androidtechnicaltest.ui.pupil.PupilListFragment

class MainActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityMainBinding
//    private lateinit var navHostFragment: NavHostFragment
//    private lateinit var navController: NavController
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val fm = supportFragmentManager
            fm.beginTransaction()
                    .add(R.id.container, PupilListFragment())
                    .commit()
        }

//        navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        navController = findNavController(R.id.fragmentContainerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_reset) {
            showFragment(PupilListFragment())
        }

        if (item.itemId == R.id.create_pupil) {
            showFragment(CreatePupilFragment())
        }

        if (item.itemId == R.id.delete_pupil) {
            showFragment(DeletePupilFragment())
        }

        if (item.itemId == R.id.get_pupil_detail) {
            showFragment(PupilDetailFragment())
        }
        return super.onOptionsItemSelected(item)
    }



    private fun showFragment(fragment: Fragment) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }
}