package com.example.shreyasencoratest.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.shreyasencoratest.R
import com.example.shreyasencoratest.ui.viewmodels.SongDetailViewModel
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SongDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SongDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var isSongPlaying = false

    private lateinit var songDetailViewModel: SongDetailViewModel

    private lateinit var txtSongTitle: AppCompatTextView
    private lateinit var txtSongDesc: AppCompatTextView
    private lateinit var imgSongPhoto: AppCompatImageView
    private lateinit var btnPlayPause: AppCompatButton

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var dataSourceFactory: DefaultDataSourceFactory

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
        return inflater.inflate(R.layout.fragment_song_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txtSongTitle = view.findViewById(R.id.txt_song_title)
        txtSongDesc = view.findViewById(R.id.txt_song_desc)
        imgSongPhoto = view.findViewById(R.id.img_song_photo)
        btnPlayPause = view.findViewById(R.id.btn_play_pause)

        songDetailViewModel = ViewModelProvider(this).get(SongDetailViewModel::class.java)
        arguments?.let {
            songDetailViewModel.itemSong.postValue(it.getParcelable(itemName))
        }

        songDetailViewModel.itemSong.observe(viewLifecycleOwner, {
            txtSongTitle.text = it.strTitle
            txtSongDesc.text = Html.fromHtml(it.strContent)
            Glide.with(requireActivity()).load(it.strImagURL).into(imgSongPhoto)

            mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(songDetailViewModel.itemSong.value?.strAudioLink))
            simpleExoPlayer.prepare(mediaSource)

            btnPlayPause.setOnClickListener{
                if(isSongPlaying)
                    pauseMedia() else playMedia()
            }
        })

        // Music Setup
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(activity)
        dataSourceFactory =
            DefaultDataSourceFactory(activity, Util.getUserAgent(activity, "exoPlayerSample"))
    }

    private fun playMedia() {
        simpleExoPlayer.playWhenReady = true
        isSongPlaying = true
        btnPlayPause.text = getString(R.string.pause)
    }

    private fun pauseMedia() {
        simpleExoPlayer.playWhenReady = false
        isSongPlaying = false
        btnPlayPause.text = getString(R.string.play)
    }

    override fun onDestroy() {
        simpleExoPlayer.playWhenReady = false
        super.onDestroy()
    }

    companion object {

        const val itemName: String = "Song"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SongDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SongDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}