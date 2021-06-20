package com.deevvdd.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.deevvdd.asteroidradar.R
import com.deevvdd.asteroidradar.databinding.FragmentMainBinding
import com.deevvdd.asteroidradar.domain.model.Asteroid
import com.deevvdd.asteroidradar.utils.AsteroidFilterType
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment(), AsteroidAdapterCallback {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var adapter: AsteroidAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        adapter = AsteroidAdapter(this)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MainFragment.viewModel
            adapter = this@MainFragment.adapter
        }
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_all_menu -> {
                viewModel.setFilterType(AsteroidFilterType.WEEKLY)
            }
            R.id.show_rent_menu -> {
                viewModel.setFilterType(AsteroidFilterType.TODAY)
            }
            R.id.show_buy_menu -> {
                viewModel.setFilterType(AsteroidFilterType.ALL)
            }
        }
        return true
    }

    override fun onItemClickListener(item: Asteroid) {
        findNavController().navigate(MainFragmentDirections.actionShowDetail(item))
    }
}
