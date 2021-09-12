package com.example.imagesearchapp.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.imagesearchapp.R
import com.example.imagesearchapp.databinding.GalleryFragmentBinding
import com.example.imagesearchapp.ui.main.adapter.UnsplashFooterLoadStateAdapter
import com.example.imagesearchapp.ui.main.adapter.UnsplashPagingAdapter
import com.example.imagesearchapp.ui.main.data.UnsplashPhoto
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.gallery_fragment), UnsplashPagingAdapter.OnItemClickListener {

    private val viewModel by viewModels<GalleryViewModel>()
    private lateinit var adapter: UnsplashPagingAdapter
    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = GalleryFragmentBinding.bind(view)
        adapter = UnsplashPagingAdapter(this)

        binding.apply {
            rvImageSearchList.setHasFixedSize(true)
            rvImageSearchList.itemAnimator = null
            rvImageSearchList.adapter = adapter.withLoadStateHeaderAndFooter(
                header = UnsplashFooterLoadStateAdapter { adapter.retry() },
                footer = UnsplashFooterLoadStateAdapter { adapter.retry() }
            )
            btnRetry.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                pbCentralLoader.isVisible = loadState.source.refresh is LoadState.Loading
                rvImageSearchList.isVisible = loadState.source.refresh is LoadState.NotLoading
                btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                tvErrorMessage.isVisible = loadState.source.refresh is LoadState.Error

                if(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && adapter.itemCount < 1) {
                    tvNoResultsFound.isVisible = true
                    rvImageSearchList.isVisible = false
                } else {
                    tvNoResultsFound.isVisible = false
                }
            }
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gallery_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search_view)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query != null) {
                    binding.rvImageSearchList.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(unsplashPhoto: UnsplashPhoto) {
        val action = GalleryFragmentDirections.actionGalleryFragmentToPhotoDetailFragment(unsplashPhoto)
        findNavController().navigate(action)
    }
}