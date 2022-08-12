package com.example.noteappmvvmkotlin.ui.Fragments

import android.os.Binder
import android.os.Bundle
import android.text.format.DateFormat
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.noteappmvvmkotlin.MainActivity
import com.example.noteappmvvmkotlin.Model.Notes
import com.example.noteappmvvmkotlin.R
import com.example.noteappmvvmkotlin.databinding.FragmentEditNoteBinding
import com.example.noteappmvvmkotlin.databinding.ItemNotesBinding
import com.example.noteappmvvmkotlin.viewModel.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*


class EditNoteFragment : Fragment() {

    val oldNotes by navArgs<EditNoteFragmentArgs>()
    lateinit var binding: FragmentEditNoteBinding
    var priority: String = "1"
    val viewModel: NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEditNoteBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        binding.edtTitle.setText (oldNotes.data.title)
        binding.edtSubtitle.setText(oldNotes.data.subTitle)
        binding.edtNotes.setText(oldNotes.data.notes)

        when (oldNotes.data.priority) {
            "1" -> {
                priority = "1"
                binding.pGreen.setImageResource(R.drawable.ic_done)
                binding.pRed.setImageResource(0)
                binding.pYellow.setImageResource(0)
            }
            "2" -> {

                priority = "2"
                binding.pYellow.setImageResource(R.drawable.ic_done)
                binding.pRed.setImageResource(0)
                binding.pGreen.setImageResource(0)

            }
            "3" -> {
                priority = "3"
                binding.pRed.setImageResource(R.drawable.ic_done)
                binding.pGreen.setImageResource(0)
                binding.pYellow.setImageResource(0)

            }
        }
        binding.pGreen.setOnClickListener {
            priority = "1"
            binding.pGreen.setImageResource(R.drawable.ic_done)
            binding.pRed.setImageResource(0)
            binding.pYellow.setImageResource(0)
        }

        binding.pYellow.setOnClickListener {
            priority = "2"
            binding.pYellow.setImageResource(R.drawable.ic_done)
            binding.pRed.setImageResource(0)
            binding.pGreen.setImageResource(0)

        }
        binding.pRed.setOnClickListener {
            priority = "3"
            binding.pRed.setImageResource(R.drawable.ic_done)
            binding.pGreen.setImageResource(0)
            binding.pYellow.setImageResource(0)

        }
        binding.btnEditNotes.setOnClickListener {
            updateNotes(it)
        }

        return binding.root

    }


    private fun updateNotes(it: View?) {
        val title = binding.edtTitle.text.toString()
        val subtitle = binding.edtSubtitle.text.toString()
        val notes = binding.edtNotes.text.toString()

        val d = Date()
        val notesDate: CharSequence = DateFormat.format("d MMMM yyyy", d.time)
        val data = Notes(
            oldNotes.data.id,
            title = title,
            subTitle = subtitle,
            notes = notes,
            date = notesDate.toString(),
            priority
        )
        viewModel.updateNotes(data)
        Toast.makeText(requireContext(), "Notes Updated Succesfully", Toast.LENGTH_LONG).show()
        Navigation.findNavController(it!!).navigate(R.id.action_editNoteFragment_to_homeFragment)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_delete){
            val bottomSheet:BottomSheetDialog = BottomSheetDialog(requireContext(),R.style.BottomSheetStyle)
            bottomSheet.setContentView(R.layout.dialod_delete)

            val tvYes = bottomSheet.findViewById<TextView>(R.id.btnYes)
            val tvNo = bottomSheet.findViewById<TextView>(R.id.btnNo)

            tvYes?.setOnClickListener {
            viewModel.deleteNotes(oldNotes.data.id!!)
                bottomSheet.dismiss()

                Navigation.findNavController(it!!).navigate(R.id.action_editNoteFragment_to_homeFragment)


            }
            tvNo?.setOnClickListener {
            bottomSheet.dismiss()
            }

            bottomSheet.show()
        }
        return super.onOptionsItemSelected(item)
    }


}