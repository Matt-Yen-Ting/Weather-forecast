package com.weather.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.weather.weatherforecast.databinding.DataListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DataListFragment : Fragment(R.layout.data_list_fragment) {

    private val binding by viewBinding(DataListFragmentBinding::bind)

    private val viewModel by viewModels<DataListViewModel>()

    private val dataAdapter = DataAdapter { data ->
        findNavController().navigate(
            DataListFragmentDirections.actionDataListFragmentToShowWeatherInfoFragment(
                data
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvDataList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDataList.adapter = dataAdapter
        viewModel.getWeatherData()
        observeData()
    }


    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.enableAppCount.filter { it < 2 }.collect { count ->
                    viewModel.setOnceEnableAppCount(count + 1)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.weatherData.collect { dataList ->
                    dataAdapter.setData(dataList)
                }
            }
        }
    }
}