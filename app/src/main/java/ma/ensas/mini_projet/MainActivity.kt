package ma.ensas.mini_projet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.imageview.ShapeableImageView
import ma.ensas.mini_projet.databinding.ActivityMainBinding
import ma.ensas.mini_projet.viewModels.HeaderViewModel
import ma.ensas.mini_projet.viewModels.LoginViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var headerViewModel: HeaderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        val headerView = navView.getHeaderView(0)
        val nameTextView = headerView.findViewById<TextView>(R.id.nameTextView)
        val emailTextView = headerView.findViewById<TextView>(R.id.emailTextView)
        val profileShapeableImage = headerView.findViewById<ShapeableImageView>(R.id.user_profile)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        headerViewModel = ViewModelProvider(this)[HeaderViewModel::class.java]

        headerViewModel.username.observe(this) { username ->
            nameTextView.text = username
        }
        headerViewModel.email.observe(this) { email ->
            emailTextView.text = email
        }
//        headerViewModel.imageResId.observe(this) { imageResId ->
//            imageResId?.let {
//                profileShapeableImage.setImageResource(imageResId)
//            }
//        }

        headerViewModel.getHeaderDetails()

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_reservation, R.id.nav_profile, R.id.nav_logout
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_reservation -> {
                    navController.navigate(R.id.fragment_reservation)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_home->{
                    navController.navigate(R.id.homeFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_profile->{
                    navController.navigate(R.id.profileFragment)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_logout->{
                    try {
                        loginViewModel.logout()
                        navController.navigate(R.id.welcomeFragment)
                        restartApp()
                    } catch (e: Exception) {
                        Log.e("LogOut", "Error during logout: ${e.message}")
                        Toast.makeText(this, "Error during logout", Toast.LENGTH_SHORT).show()
                    }
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                else -> false
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun hideAppBar() {
        supportActionBar?.hide()
    }

    fun showAppBar() {
        supportActionBar?.show()
    }

    private fun restartApp() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}