package com.weather.weatherforecast

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.weather.weatherforecast.databinding.ShowWeatherFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShowWeatherInfoFragment : Fragment(R.layout.show_weather_fragment) {

    private val binding by viewBinding(ShowWeatherFragmentBinding::bind)

    private val viewModel by viewModels<ShowWeatherInfoViewModel>()

    private val args by navArgs<ShowWeatherInfoFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvStartTime.text = args.weatherInfo.startTime
        binding.tvEndTime.text = args.weatherInfo.endTime
        binding.tvTemperature.text = args.weatherInfo.temperature
        observeData()

    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.enableAppCount.filter { it == 2 }.collect {
                    showWelcomeMessage()
                }
            }
        }
    }

    private fun showWelcomeMessage() {
        MaterialAlertDialogBuilder(requireContext()).setMessage(getString(R.string.welcome_back))
            .setPositiveButton(getString(R.string.ok), null).show()
    }
}