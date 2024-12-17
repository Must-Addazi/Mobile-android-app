package ma.ensas.mini_projet.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ma.ensas.mini_projet.MainActivity
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding : FragmentLoginBinding get() = _binding!!
    private var username: String? = null
    private var password: String? = null

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

        binding.registerHyperlink.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.login.setOnClickListener {
            username = binding.username.text.toString()
            password = binding.password.text.toString()

            try {
                handleLogin(username!!, password!!)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            } catch (ex: Exception) {
                binding.errorMsg.text = "invalid credentials!"
            }
        }

    }

    private fun handleLogin(username: String, password: String) {

        if(username.isEmpty() or username.isBlank() or password.isEmpty() or password.isBlank()) {
            throw Exception("Credentials aren't correct")
        }
        else if(username == "user" && password == "user") {
            println("Logged in successfully")
        }
        else {
            throw Exception("Credentials aren't correct")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}