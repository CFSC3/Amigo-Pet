package com.ifam.amigopetfixo.activity

import androidx.annotation.DrawableRes

data class Tip(
        val title: String,
        @DrawableRes val logo: Int,
        /*@DrawableRes val background: Int,*/
        val color: Int
)