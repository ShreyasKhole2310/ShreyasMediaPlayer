package com.example.shreyasencoratest.ui.fragment

interface DataUpdates {
    fun showError(title:String,message:String)
    fun updateSongStored(value:Boolean=true)

}