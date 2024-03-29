package com.example.fit4you_android.ui.view.basicstatuscheck.questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.fit4you_android.R
import com.example.fit4you_android.databinding.FragmentUserHistoryBinding
import com.example.fit4you_android.ui.base.BaseFragment
import com.example.fit4you_android.ui.view.basicstatuscheck.BaseBasicQuestionViewModel

class UserHistoryFragment : BaseFragment<FragmentUserHistoryBinding, BaseBasicQuestionViewModel>() {
    override val layoutResourceId: Int
        get() = R.layout.fragment_user_history
    override val viewModel: BaseBasicQuestionViewModel by activityViewModels()

    override fun initBeforeBinding() {

    }

    override fun initAfterBinding() {

    }

    override fun initView() {
        binding.cbTurtle.setOnCheckedChangeListener { _, isChecked ->
            updateDiag("거북목", isChecked)
        }

        binding.cbRotator.setOnCheckedChangeListener { _, isChecked ->
            updateDiag("회전근개", isChecked)
        }

        binding.cbDisc.setOnCheckedChangeListener { _, isChecked ->
            updateDiag("허리디스크", isChecked)
        }

        binding.cbChuck.setOnCheckedChangeListener { _, isChecked ->
            updateDiag("척추관협착증", isChecked)
        }

        binding.cbGolf.setOnCheckedChangeListener { _, isChecked ->
            updateDiag("골프엘보우", isChecked)
        }
        binding.cbKnee.setOnCheckedChangeListener { _, isChecked ->
            updateDiag("무릎질병", isChecked)
        }
    }

    private fun updateDiag(value: String, isChecked: Boolean) {
        val currentReq = viewModel.baseQuestionReq.value!!
//        val currentReq = viewModel.survey.value
        if (isChecked) {
            currentReq.hist = currentReq.hist + value
        } else {
            currentReq.hist = currentReq.hist.filter { it != value }
        }
        viewModel.baseQuestionReq.value = currentReq
//        viewModel.setValue(currentReq)
    }
}