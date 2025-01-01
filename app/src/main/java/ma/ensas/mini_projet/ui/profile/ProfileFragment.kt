package ma.ensas.mini_projet.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import ma.ensas.mini_projet.utils.ImageConverter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import ma.ensas.mini_projet.R
import java.io.File
import java.io.FileOutputStream
import java.text.ParseException


class ProfileFragment : Fragment() {
    private lateinit var _binding: FragmentProfileBinding
    private val binding: FragmentProfileBinding get() = _binding
    private lateinit var viewModel: ProfileViewModel

    private var currentUserId = -1

    @SuppressLint("SimpleDateFormat")
    val format = SimpleDateFormat("yyyy-MM-dd")

    // Store selected image as a ByteArray
    private var selectedImageByteArray: ByteArray? = null

    // Activity Result Launcher for picking an image
    @SuppressLint("SetTextI18n")
//    private val pickImageLauncher = registerForActivityResult(
//        ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            try {
//                // Convert the selected image to Bitmap
//                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
//                // Convert Bitmap to ByteArray
//                selectedImageByteArray = ImageConverter.bitmapToByteArray(bitmap)
//
//                binding.userProfile.setImageBitmap(bitmap)
//            } catch (ex: Exception) {
//                binding.errorMsg.text = "Failed to load image: ${ex.message}"
//                Log.e("ProfileFragment", "Error loading image: ${ex.message}")
//            }
//        }
//    }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
                val imagePath = saveImageToInternalStorage(requireContext(), bitmap, currentUserId)

                binding.userProfile.setImageBitmap(bitmap)
                viewModel.updateUserImageUri(currentUserId, imagePath) // Save the path in the database

            } catch (ex: Exception) {
                binding.errorMsg.text = "Failed to load image: ${ex.message}"
                Log.e("ProfileFragment", "Error loading image: ${ex.message}")
            }
        }
    }


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
                currentUserId = userDetails.userId
                binding.emailEditText.setText(userDetails.email)
                binding.usernameEditText.setText(userDetails.username)
                binding.phoneNumberEditText.setText(userDetails.phoneNumber)
                binding.passwordEditText.setText(userDetails.password)
                binding.roleTextview.text = userDetails.role.name
                binding.usernameTextview.text = userDetails.username

                if(userDetails.birthDate != null) {
                    binding.birthDateEditText.setText(format.format(userDetails.birthDate))
                }

                // binding.userProfile.setImageResource(userDetails.imageResId)
            } else {
                binding.errorMsg.text = "User details not found."
            }
        }
        viewModel.getUserDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun applyChanges() {
        binding.uploadImageBtn.setOnClickListener {
            // Launch the image picker
            pickImageLauncher.launch("image/*")
        }

        binding.saveBtn.setOnClickListener {
            try {
                val email = binding.emailEditText.text.toString()
                val username = binding.usernameEditText.text.toString()
                val phoneNumber = binding.phoneNumberEditText.text.toString()
                val password = binding.passwordEditText.text.toString()

                try {
                    val birthDate = format.parse(binding.birthDateEditText.text.toString())
                    val updatedUser = User(
                        username = username,
                        email = email,
                        password = password,
                        phoneNumber = phoneNumber,
                        birthDate = birthDate,
                        //imageResId = R.drawable.default_user_profile,
                        role = Roles.USER,
                        userId = currentUserId
                    )
                    viewModel.applyChanges(updatedUser)

                } catch (e: ParseException) {
                    binding.errorMsg.text = "Invalid date format. Use yyyy-MM-dd."
                }

                binding.errorMsg.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_color))
                binding.errorMsg.text = "Changes saved successfully."

            } catch (ex: Exception) {

                binding.errorMsg.setTextColor(ContextCompat.getColor(requireContext(), R.color.soft_red_color))
                binding.errorMsg.text = "Failed to save changes."
                Log.e("ProfileFragment", "Error saving changes: ${ex.message}")

            }
        }
    }

    fun saveImageToInternalStorage(context: Context, bitmap: Bitmap, userId: Int): String {
        val directory = File(context.filesDir, "userProfiles")
        if (!directory.exists()) directory.mkdirs()

        val file = File(directory, "user_${userId}.png")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        return file.absolutePath
    }

}