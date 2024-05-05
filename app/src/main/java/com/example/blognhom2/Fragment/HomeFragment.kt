package com.example.blognhom2.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.example.blognhom2.Adapter.PostAdapter
import com.example.blognhom2.databinding.FragmentHomeBinding
import com.example.blognhom2.model.Post
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initImageView()
        SetPostAdapter()
        // Inflate the layout for this fragment
        val view = binding.root
        return view
    }
    fun initImageView() {
        val imageList = ArrayList<SlideModel>() // Create image list


        imageList.add(SlideModel("https://kenh14cdn.com/thumb_w/660/203336854389633024/2022/5/25/photo-1-1653461340935426739125.jpg", "Chào bạn dã đến với blog của nhóm 2 chúng tôi."))
        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
    }
    private fun SetPostAdapter(){
        val postList = mutableListOf<Post>()
        val post1 = Post("May 5, 2024", "https://images.immediate.co.uk/production/volatile/sites/3/2017/07/142723.b2a13431-ff0a-4480-96a9-a1114573b3f4.jpg?quality=90&resize=620,414", "Title 1", "Category 1")
        val post2 = Post("May 4, 2024", "https://images.immediate.co.uk/production/volatile/sites/3/2017/07/142723.b2a13431-ff0a-4480-96a9-a1114573b3f4.jpg?quality=90&resize=620,414", "Title 2", "Category 2")
        val post3 = Post("May 3, 2024", "https://images.immediate.co.uk/production/volatile/sites/3/2017/07/142723.b2a13431-ff0a-4480-96a9-a1114573b3f4.jpg?quality=90&resize=620,414", "Title 3", "Category 3")

        postList.add(post1)
        postList.add(post2)
        postList.add(post3)

        val adapter = PostAdapter(postList)
        binding.PostRecyclerView.adapter = adapter
        binding.PostRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}