package com.example.noteappmvvmkotlin.ui.Fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.noteappmvvmkotlin.Model.Notes
import com.example.noteappmvvmkotlin.R
import com.example.noteappmvvmkotlin.databinding.FragmentCreateNotesBinding
import com.example.noteappmvvmkotlin.databinding.FragmentHomeBinding
import com.example.noteappmvvmkotlin.viewModel.NotesViewModel
import java.lang.String.format
import java.util.*


class CreateNotesFragment : Fragment() {
    lateinit var binding: FragmentCreateNotesBinding
    var priority: String = "1"
    val viewModel: NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNotesBinding.inflate(layoutInflater, container, false)
        binding.pGreen.setImageResource(R.drawable.ic_done)
        binding.pGreen.setOnClickListener {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_done)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }
        binding.pRed.setOnClickListener {
            priority = "3"
            binding.pRed.setImageResource(R.drawable.ic_done)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)

        }
        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_done)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)

        }
        binding.btnSaveNotes.setOnClickListener {
            createNotes(it)
        }


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun createNotes(it: View?) {
        val title = binding.edtTitle.text.toString()
        val subtitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)
        val data = Notes(
            null,
            title = title,
            subTitle = subtitle,
            notes = notes,
            date = notesDate.toString(),
            priority
        )
        viewModel.addNotes(data)
        Toast.makeText(requireContext(),"Notes Created Succesfull",Toast.LENGTH_LONG).show()
        Navigation.findNavController(it!!).navigate(R.id.action_createNotesFragment_to_homeFragment)

    }

}