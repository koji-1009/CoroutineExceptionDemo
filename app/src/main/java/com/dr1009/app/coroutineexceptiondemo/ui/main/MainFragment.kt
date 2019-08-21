package com.dr1009.app.coroutineexceptiondemo.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dr1009.app.coroutineexceptiondemo.R

class MainFragment : Fragment(R.layout.main_fragment) {

    private val viewModel by viewModels<MainViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
