package ma.ensas.mini_projet.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ma.ensas.mini_projet.R
import ma.ensas.mini_projet.databinding.FragmentRegisterBinding
import ma.ensas.mini_projet.databinding.FragmentWelcomeBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private var username: String? = null
    private var password: String? = null
    private var password2: String? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginHyperlink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.register.setOnClickListener {
            username = binding.username.text.toString()
            password = binding.password.text.toString()
            password2 = binding.confirmPassword.text.toString()
            email = binding.email.text.toString()

            try {
                handleRegistration(username!!, email!!, password!!, password2!!)
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            } catch (ex: Exception) {
                binding.errorMsg.text = "invalid credentials!"
            }
        }
    }

    private fun handleRegistration(username: String, email: String, password: String, password2: String) {

        if(username.isEmpty() or username.isBlank() or password.isEmpty() or password.isBlank()
            or password2.isEmpty() or password2.isBlank() or email.isEmpty() or email.isBlank())
        {
            throw Exception("Credentials aren't correct")
        }
        else {
            throw Exception("Credentials aren't correct")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}