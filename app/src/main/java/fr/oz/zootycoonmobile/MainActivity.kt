package fr.oz.zootycoonmobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.oz.zootycoonmobile.fragments.AcceuilFragment
import fr.oz.zootycoonmobile.fragments.CreerActionFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(AcceuilFragment(context = this))

        val navigationView = findViewById<BottomNavigationView>(R.id.navBar)
        navigationView.getMenu().getItem(1).setChecked(true);
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home_page -> {
                    loadFragment(AcceuilFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.add_page -> {
                    loadFragment(CreerActionFragment(this))
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.back_page -> {
                    this.supportFragmentManager.popBackStack()
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }


    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

}