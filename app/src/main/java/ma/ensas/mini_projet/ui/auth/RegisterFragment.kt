package ma.ensas.mini_projet.ui.auth

import android.annotation.SuppressLint
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
import ma.ensas.mini_projet.databinding.FragmentRegisterBinding
import ma.ensas.mini_projet.viewModels.RegistrationViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private lateinit var registrationViewModel: RegistrationViewModel
    private val binding get() = _binding!!

    private lateinit var username: String
    private lateinit var password: String
    private lateinit var password2: String
    private lateinit var email: String

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

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registrationViewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        registrationViewModel.registrationStatus.observe(viewLifecycleOwner, Observer { success ->
            if(success) {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        })

        binding.register.setOnClickListener {
            username = binding.username.text.toString()
            password = binding.password.text.toString()
            password2 = binding.confirmPassword.text.toString()
            email = binding.email.text.toString()

            try {
                if(username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && password2.isNotEmpty())
                    registrationViewModel.registerUser(username, email, password, password2)
                else {
                    binding.errorMsg.text = "Please fill all fields"
                }

            } catch (ex: Exception) {
                binding.errorMsg.text = "invalid credentials!"
            }
        }

        binding.loginHyperlink.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}