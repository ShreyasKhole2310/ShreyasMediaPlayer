package com.example.shreyasencoratest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shreyasencoratest.R
import com.example.shreyasencoratest.beanclasses.Entry
import com.example.shreyasencoratest.database.SongEntity
import com.example.shreyasencoratest.ui.fragment.ItemClickListner

class SongListAdapter(
    private val itemClickListner: ItemClickListner
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var lstSongs: ArrayList<SongEntity>? = ArrayList<SongEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        SongViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_song_item, parent,false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        lstSongs?.let { (holder as SongViewHolder).bind(it.get(position)) }
    }

    override fun getItemCount(): Int = lstSongs?.size!!

    internal inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val txtSongTitle: AppCompatTextView = itemView.findViewById(R.id.txt_song_title)
        private val txtSongArtist: AppCompatTextView = itemView.findViewById(R.id.txt_song_artist)

        fun bind(itmSong: SongEntity){
            txtSongTitle.text = itmSong.strTitle
            txtSongArtist.text = itmSong.strAuthor
            itemView.setOnClickListener {
                itemClickListner.onItemClick(itmSong)
            }
        }
    }
}