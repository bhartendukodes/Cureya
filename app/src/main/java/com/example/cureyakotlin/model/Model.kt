package com.example.cureyakotlin.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Model(
    val id:String,
    val name:String,
    val gmail:String
):Parcelable
