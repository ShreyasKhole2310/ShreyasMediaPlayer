package com.example.shreyasencoratest.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shreyasencoratest.R
import com.example.shreyasencoratest.beanclasses.Entry
import com.example.shreyasencoratest.database.SongEntity
import com.example.shreyasencoratest.ui.adapters.SongListAdapter
import com.example.shreyasencoratest.ui.viewmodels.SongListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SongListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SongListFragment : Fragment(), DataUpdates {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rvSongList: RecyclerView
    private lateinit var adptrSongList: SongListAdapter

    private lateinit var vmSongList: SongListViewModel
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvSongList = view.findViewById(R.id.rv_song_list)

        adptrSongList = SongListAdapter(object: ItemClickListner{
            override fun onItemClick(song: SongEntity?) {
                val bundle =
                    bundleOf(SongDetailFragment.itemName to song)
                findNavController().navigate(R.id.songDetailFragment, bundle)
            }

        })
        vmSongList = ViewModelProvider(this).get(SongListViewModel::class.java)

        vmSongList.mutableSongList.observe(viewLifecycleOwner, {
            adptrSongList.lstSongs = it as ArrayList<SongEntity>?
            with(rvSongList) {
                adapter = adptrSongList
                layoutManager = GridLayoutManager(requireActivity(), 2)
            }
        })

        vmSongList.dataUpdates = this

        sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)!!
        val isStored = sharedPref.getBoolean(getString(R.string.isStoredBoolean),false)
        vmSongList.getSongs(isStored)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SongListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SongListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun showError(title: String, message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun updateSongStored(value: Boolean) {
        with(sharedPref.edit()) {
            putBoolean(getString(R.string.isStoredBoolean), value)
            commit()
        }
    }
}