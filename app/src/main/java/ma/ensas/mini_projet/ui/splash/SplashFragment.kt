package ma.ensas.mini_projet.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ma.ensas.mini_projet.MainActivity
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.databinding.FragmentSplashBinding
import ma.ensas.mini_projet.utils.SessionManager
import ma.ensas.mini_projet.viewModels.SplashViewModel

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    val binding: FragmentSplashBinding? get() = _binding
    private lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.hideAppBar()
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.showAppBar()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        splashViewModel.isAlreadyAuthenticated()
        
        splashViewModel.userAlreadyLoggedIn.observe(viewLifecycleOwner, Observer { userAuthenticated ->
            if(userAuthenticated) {
                android.os.Handler().postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                }, 3000)
            }
            else {
                android.os.Handler().postDelayed({
                    findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
                }, 3000)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}