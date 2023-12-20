package com.incomeify.incomeifyapp.ui.dashboard.saved

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.incomeify.incomeifyapp.data.local.SavedIncome
import com.incomeify.incomeifyapp.databinding.FragmentSavedBinding
import com.incomeify.incomeifyapp.domain.repository.SavedRepository
import com.incomeify.incomeifyapp.domain.viewmodel.SavedViewModel
import com.incomeify.incomeifyapp.domain.viewmodel.SavedViewModelFactory
import com.incomeify.incomeifyapp.ui.dashboard.saved.adapter.SavedAdapter
import com.incomeify.incomeifyapp.ui.dashboard.saved.detail.DetailSavedActivity
import com.incomeify.incomeifyapp.utils.Constants.DETAIL_SAVED

class SavedFragment : Fragment(), SavedAdapter.OnItemClickListener {

    private lateinit var binding: FragmentSavedBinding
    private val savedRepository: SavedRepository by lazy {
        SavedRepository.getInstance(requireContext())
    }
    private val savedViewModel: SavedViewModel by lazy {
        ViewModelProvider(this, SavedViewModelFactory(savedRepository))[SavedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedViewModel.getSavedIncomeList()

        savedViewModel.getSavedIncomeList().observe(viewLifecycleOwner) { savedIncomeList ->
            setupRecyclerView(savedIncomeList)
        }
    }

    private fun setupRecyclerView(savedIncomeList: List<SavedIncome>) {
        with(binding) {
            rvSavedItem.setHasFixedSize(true)
            rvSavedItem.layoutManager = LinearLayoutManager(requireContext())
            rvSavedItem.adapter = SavedAdapter(requireContext(), savedIncomeList).apply {
                listener = this@SavedFragment
            }
        }
    }

    override fun onItemClicked(item: SavedIncome) {
        val intent = Intent(requireContext(), DetailSavedActivity::class.java)
        intent.putExtra(DETAIL_SAVED, item)
        startActivity(intent)
    }
}