package com.dev.lifeordead.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.lifeordead.R
import com.dev.lifeordead.databinding.FragmentHomeBinding
import com.dev.lifeordead.presentation.adapter.HomeAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = HomeAdapter()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HomeFragment.adapter
        }
    }

    private fun setupObservers() {
        viewModel.cells.onEach { cells ->
            adapter.submitList(cells) {
                binding.recyclerView.post {
                    binding.recyclerView.scrollToPosition(cells.size - 1)
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun setupListeners() {
        binding.btnCreate.setOnClickListener {
            viewModel.addCeil()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}