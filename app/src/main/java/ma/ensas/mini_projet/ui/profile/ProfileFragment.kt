package ma.ensas.mini_projet.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ma.ensas.mini_projet.data.entities.User
import ma.ensas.mini_projet.databinding.FragmentProfileBinding
import ma.ensas.mini_projet.utils.enumerations.Roles
import ma.ensas.mini_projet.viewModels.ProfileViewModel
import java.text.SimpleDateFormat

class ProfileFragment : Fragment() {

    private lateinit var _binding : FragmentProfileBinding
    private val binding : FragmentProfileBinding get() = _binding
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeUserDetails()
        applyChanges()
    }

    private fun initializeUserDetails() {

        viewModel.userDetails.observe(viewLifecycleOwner) { userDetails ->
            if (userDetails != null) {
                binding.emailEditText.setText(userDetails.email)
                binding.usernameEditText.setText(userDetails.username)
                binding.birthDateEditText.setText(userDetails.birthDate.toString())
                binding.phoneNumberEditText.setText(userDetails.phoneNumber)

            } else {
                binding.errorMsg.text = "User details not found."
            }
        }
        viewModel.getUserDetails()
    }

    private fun applyChanges() {
        val format = SimpleDateFormat("yyyy-MM-dd")

        val email = binding.emailEditText.text.toString()
        val username = binding.usernameEditText.text.toString()
        val birthDate = binding.birthDateEditText.text.toString()
        val phoneNumber = binding.phoneNumberEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        binding.saveBtn.setOnClickListener {
            try {
                viewModel.applyChanges(
                    User(username,
                        email,
                        password,
                        phoneNumber,
                        format.parse(birthDate),
                        null,
                        Roles.USER,
                    )
                )
            }
            catch (ex: Exception) {
                binding.errorMsg.text = "Failed To Save Changes"
                Log.e("profileSaveChanges", "Failed To Save User: ${ex.message}")
            }
        }
    }

}