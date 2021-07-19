package com.velord.utilshubdemo.ui.fragment.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.velord.utilshubdemo.R

class MainFragment : Fragment(R.layout.main_fragment) {
    
    companion object {
        fun newInstance() = MainFragment()
    }
    
    private val viewModel: MainViewModel by viewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        viewModel
        initView()
    }
    
    private fun initView() {
        //createDefaultScope()
    }
}