package com.example.imagesearchapp.ui.main

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.FragmentPhotoDetailBinding

/**
 * A simple [Fragment] subclass.
 * Use the [PhotoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotoDetailFragment : Fragment(R.layout.fragment_photo_detail) {

    private val args by navArgs<PhotoDetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photo = args.unsplashPhoto
        val uri = Uri.parse(photo.user.attributionUrl)
        val intent = Intent(Intent.ACTION_VIEW, uri)

        val binding = FragmentPhotoDetailBinding.bind(view)
        binding.apply {
            Glide.with(this@PhotoDetailFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_error_24)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbCentralLoader.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        pbCentralLoader.isVisible = false
                        tvUsername.isVisible = true
                        tvDesc.isVisible = photo.desc != null
                        return false
                    }

                })
                .into(binding.ivLargePhoto)

            tvUsername.text = photo.user.name
            tvDesc.text = photo.desc
        }


    }
}