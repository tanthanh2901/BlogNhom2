package com.example.blognhom2.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blognhom2.Fragment.PostPendingContentFragment
import com.example.blognhom2.R
import com.example.blognhom2.model.PostInfo

class PostConfirmAdapter(var postList : List<PostInfo>) : RecyclerView.Adapter<PostConfirmAdapter.PostConfirmViewHolder>() {
    var genericList : MutableList<PostInfo> = postList as MutableList<PostInfo>

    inner class PostConfirmViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val postImage: ImageView = itemView.findViewById(R.id.articleImage)
        val postTitle: TextView = itemView.findViewById(R.id.articleTitle)
        var postTime: TextView = itemView.findViewById(R.id.articleDateTime)
        var postCategory: TextView = itemView.findViewById(R.id.articleCategories)
        val postContent : TextView = itemView.findViewById(R.id.articleDescription)
    }
    public fun setFilteredList(filteredList: List<PostInfo>){
        this.genericList = filteredList as MutableList<PostInfo>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostConfirmViewHolder {
        return PostConfirmViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        println(genericList.size)
        return genericList.size
    }



    // do some thing
    override fun onBindViewHolder(holder: PostConfirmViewHolder, position: Int) {
        holder.itemView.apply {
            holder.postTitle.text = genericList[position].title
            holder.postTime.text = genericList[position].time
            holder.postCategory.text = genericList[position].category
            holder.postContent.text = genericList[position].content
            Glide.with(holder.itemView.context)
                .load(genericList[position].img)
                .into(holder.postImage)

            setOnClickListener {
                val post = genericList[position]
                val fragment = PostPendingContentFragment() // Replace YourFragment with your actual fragment class
                fragment.setData(post)
                val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frame_layout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}