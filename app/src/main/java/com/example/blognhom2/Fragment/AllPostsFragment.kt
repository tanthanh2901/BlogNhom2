package com.example.blognhom2.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blognhom2.API.AdminApi
import com.example.blognhom2.Adapter.PostOwnerAdapter
import com.example.blognhom2.databinding.FragmentAllPostsBinding
import com.example.blognhom2.model.PostInfo
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.DriverManager

class AllPostsFragment : Fragment() {
    private var isLoading = false
    private var visibleThreshold = 5 // Number of items from the bottom of the list at which loading more is triggered
    private var offset = 0 // The offset for loading more posts

    private var _binding: FragmentAllPostsBinding? = null

    lateinit var adapter : PostOwnerAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllPostsBinding.inflate(inflater, container, false)

        preparePostData()
        SetPostAdapter()
        // Inflate the layout for this fragment
        val view = binding.root
        return view
    }

    private var postList = mutableListOf<PostInfo>()

    private fun preparePostData() : List<PostInfo> {
        postList.clear()

        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val orginal : Request = chain.request()
                val requestBuilder = orginal.newBuilder()

                val cookies = CookieManager.getInstance().getCookie("http://10.0.2.2:8081/")

                DriverManager.println("Cookies $cookies")
                if(cookies != null) {
                    requestBuilder.addHeader("Cookie", cookies)
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        val api = retrofit.create(AdminApi::class.java)

        val call = api.getPosts(0)

        call.enqueue(object : Callback<List<PostInfo>> {
            override fun onResponse(call: Call<List<PostInfo>>, response: Response<List<PostInfo>>) {
                println("ResponsePost")
                println(response)
                if (!response.isSuccessful) {
                    println("Code: " + response.code())
                    return
                }

                val posts = response.body()
                posts?.let {
                    postList.addAll(it)
                }
                updateAdapter()
            }

            override fun onFailure(call: Call<List<PostInfo>>, t: Throwable) {
                println(t.message)
            }
        })

        return postList
    }

    private fun updateAdapter() {
        adapter.setFilteredList(postList)
    }
    private fun SetPostAdapter(){
        adapter = PostOwnerAdapter(postList)
        binding.PostRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        binding.PostRecyclerView.layoutManager = layoutManager

        // Add the onScrollListener to your RecyclerView
        binding.PostRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    // End has been reached, load more items
                    loadMoreItems()
                    isLoading = true
                }
            }
        })
    }

    private fun loadMoreItems() {
        // Increase your offset
        offset += 1

        // Call your API here
        val httpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val orginal : Request = chain.request()
                val requestBuilder = orginal.newBuilder()

                val cookies = CookieManager.getInstance().getCookie("http://10.0.2.2:8081/")

                DriverManager.println("Cookies $cookies")
                if(cookies != null) {
                    requestBuilder.addHeader("Cookie", cookies)
                }

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        val api = retrofit.create(AdminApi::class.java)

        val call = api.getPosts(offset)

        call.enqueue(object : Callback<List<PostInfo>> {
            override fun onResponse(call: Call<List<PostInfo>>, response: Response<List<PostInfo>>) {
                println(response)
                if (response.isSuccessful) {
                    val posts = response.body()
                    posts?.let {
                        postList.addAll(it)
                        adapter.notifyDataSetChanged()
                    }
                }
                isLoading = false
            }

            override fun onFailure(call: Call<List<PostInfo>>, t: Throwable) {
                // Handle the error
                isLoading = false
            }
        })
    }

}