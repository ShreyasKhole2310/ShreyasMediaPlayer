package com.example.shreyasencoratest.ui.fragment

import com.example.shreyasencoratest.database.SongEntity

interface ItemClickListner {
    fun onItemClick(song: SongEntity?)
}