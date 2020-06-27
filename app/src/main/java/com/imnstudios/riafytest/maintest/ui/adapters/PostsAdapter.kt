package com.imnstudios.riafytest.maintest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imnstudios.riafytest.R
import com.imnstudios.riafytest.maintest.data.models.ModelDataClass

class PostsAdapter(
    private val posts: List<ModelDataClass>
) : RecyclerView.Adapter<PostsAdapter.PostsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return PostsViewHolder(view)
    }


    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {

        val posts = posts[position]


        holder.title.text = posts.title
        holder.desc.text = posts.description
    }


    inner class PostsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView
        var desc: TextView

        init {
            this.title = view.findViewById(R.id.title_tv)
            this.desc = view.findViewById(R.id.desc_tv)
        }

    }


}