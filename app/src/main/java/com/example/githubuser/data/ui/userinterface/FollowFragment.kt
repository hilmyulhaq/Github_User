package com.example.githubuser.data.ui.userinterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.ui.modelView.DetailViewModel
import com.example.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var detailViewModel: DetailViewModel
    private var position: Int = 0
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFollowBinding.bind(view)

        detailViewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        if (position == 1) {
            detailViewModel.followerUsers.observe(viewLifecycleOwner, Observer { followers ->
                val adapter = FollowAdapter()
                binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
                binding.rvFollow.adapter = adapter
                adapter.submitList(followers)
            })
            detailViewModel.getFollower(username)
        } else {
            detailViewModel.followingUsers.observe(viewLifecycleOwner, Observer { following ->
                val adapter = FollowAdapter()
                binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
                binding.rvFollow.adapter = adapter
                adapter.submitList(following)
            })
            detailViewModel.getFollowing(username)
        }
        detailViewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }
}
