package ma.ensas.mini_projet.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import ma.ensas.mini_projet.MainActivity
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.databinding.FragmentLoginBinding
import ma.ensas.mini_projet.viewModels.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var _binding : FragmentLoginBinding
    private val binding : FragmentLoginBinding get() = _binding
    private lateinit var loginViewModel: LoginViewModel

    private lateinit var username: String
    private lateinit var password: String

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
        _binding = FragmentLoginBinding.inflate(inflater, container,false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        loginViewModel.loginStatus.observe(viewLifecycleOwner, Observer { success ->
            if(success) {
                // I have to save the user's context
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            else {
                binding.errorMsg.text = "Invalid Email or Password"
            }
        })

        binding.login.setOnClickListener {
            username = binding.username.text.toString()
            password = binding.password.text.toString()

            try {
                if(username.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel.authenticateUser(username, password)
                }
                else {
                    binding.errorMsg.text = "All Fields are Required!"
                }
            } catch (ex: Exception) {
                Log.w("Login", "invalid credentials!")
                binding.errorMsg.text = "invalid credentials!"
            }
        }

        binding.registerHyperlink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}