package com.example.provalyutakursi

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

class Functions {



}

fun ImageView.setImage(url:String){
    Picasso.get().load(url).into(this)
}
@BindingAdapter("setImage:url")
fun setImageUrl(imageView: ImageView,photoUrl:String){
    Picasso.get().load(photoUrl).error(R.drawable.flag_img).into(imageView)
}