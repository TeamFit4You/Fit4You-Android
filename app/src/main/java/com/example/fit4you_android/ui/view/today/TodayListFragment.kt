package com.example.fit4you_android.ui.view.today

import android.content.Intent
import androidx.fragment.app.viewModels
import com.example.fit4you_android.R
import com.example.fit4you_android.data.model.TodayList
import com.example.fit4you_android.databinding.FragmentTodayListBinding
import com.example.fit4you_android.ui.adapter.ClickListener.OnTodayItemClickListener
import com.example.fit4you_android.ui.adapter.TodayListAdapter
import com.example.fit4you_android.ui.base.BaseFragment
import com.example.fit4you_android.ui.view.today.start.ExampleActivity
import com.example.fit4you_android.util.MarginItemDecoration
import kotlin.math.roundToInt

class TodayListFragment : BaseFragment<FragmentTodayListBinding, TodayListViewModel>(),
    OnTodayItemClickListener {
    override val viewModel: TodayListViewModel by viewModels()
    override val layoutResourceId: Int
        get() = R.layout.fragment_today_list
    private lateinit var adapter: TodayListAdapter

    override fun initBeforeBinding() {

    }

    override fun initAfterBinding() {

    }

    override fun initView() {
        val dataListTmp = arrayListOf(
            TodayList("어깨"),
            TodayList("발목"),
            TodayList("어렵네"),
            TodayList(resources.getString(R.string.today_list_ex1)),
            TodayList(resources.getString(R.string.today_list_ex2)),
            TodayList(resources.getString(R.string.today_list_ex3)),
            TodayList(resources.getString(R.string.today_list_ex4)),
            TodayList(resources.getString(R.string.today_list_ex5))
        )
        bindRVTodayListData(lists = dataListTmp)
    }

    override fun onTodayItemClick(item: TodayList) {
        val intent = Intent(requireActivity(), ExampleActivity::class.java)
        intent.putExtra("key", item.bodyPart)
        startActivity(intent)
    }

    private fun bindRVTodayListData(lists: ArrayList<TodayList>) {
        val spaceDecoration = MarginItemDecoration(
            resources.getDimension(R.dimen.today_bottom_space).roundToInt()
        )
        adapter = TodayListAdapter(this, viewModel, lists, this)
        binding.rvTodayList.adapter = adapter
        binding.rvTodayList.addItemDecoration(spaceDecoration)
    }
}