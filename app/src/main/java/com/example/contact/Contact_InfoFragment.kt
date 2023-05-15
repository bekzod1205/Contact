package com.example.contact

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.contact.database.AppDatabase
import com.example.contact.database.Contact
import com.example.contact.databinding.FragmentContactInfoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Contact_InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Contact_InfoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    val appDatabase: AppDatabase by lazy {
        AppDatabase.getInstanse(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentContactInfoBinding.inflate(inflater, container, false)
        val id = arguments?.getInt("id")
        var contacts = appDatabase.getContactDao().getAllUsers()


        var contact: Contact? = null
        for (i in contacts) {
            if (i.id == id) {
                contact = i
            }
        }
        binding.name.text = contact?.name
        binding.phone.text = contact?.phone_number

        binding.call2.setOnClickListener {
            openCall(binding.phone.text.toString())
        }
        binding.delete.setOnClickListener {
            if (contact != null) {
                appDatabase.getContactDao().deleteContact(contact)
            }
            requireActivity().onBackPressed()
        }
        binding.edit.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("idd", contact!!.id)
            findNavController().navigate(R.id.action_contact_InfoFragment_to_editFragment, bundle)
        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Contact_InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Contact_InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun openCall(number: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                1
            )
        } else {
            if (number.isNotEmpty()) {
                val callIntent = Intent(Intent.ACTION_CALL)
                callIntent.data = Uri.parse("tel:$number")
                activity?.startActivity(callIntent)
            }
        }
    }
}