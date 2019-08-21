package com.dr1009.app.coroutineexceptiondemo.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dr1009.app.coroutineexceptiondemo.R
import com.dr1009.app.coroutineexceptiondemo.databinding.MainFragmentBinding

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = MainFragmentBinding.bind(view)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

}
