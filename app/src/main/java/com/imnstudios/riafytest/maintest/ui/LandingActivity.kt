package com.imnstudios.riafytest.maintest.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.imnstudios.riafytest.R
import com.imnstudios.riafytest.maintest.data.models.ModelDataClass
import com.imnstudios.riafytest.maintest.data.network.NetworkApi
import com.imnstudios.riafytest.maintest.data.repositories.Repository
import com.imnstudios.riafytest.maintest.ui.adapters.PostsAdapter
import com.imnstudios.riafytest.utils.toast
import kotlinx.android.synthetic.main.activity_landing.*

class LandingActivity : AppCompatActivity(), ListenerInterface {

    private lateinit var factory: ViewModelFactory
    private lateinit var viewModel: ViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)


        val api = NetworkApi()
        val repository = Repository(api)

        factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ViewModel::class.java)
        viewModel.getPosts()
        viewModel.posts.observe(this, Observer { posts ->
            recycler_view.also {
                it.layoutManager = LinearLayoutManager(this)
                it.setHasFixedSize(true)
                it.adapter = PostsAdapter(posts)
            }

        })


        //fab
        fab.setOnClickListener {
            openFab()
        }
    }

    private fun openFab() {
        val dialog = Dialog(this)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.add_post_dialog)
        dialog.setCancelable(true)
        dialog.show()
        val titleEt = dialog.findViewById<EditText>(R.id.title_et)
        val descEt = dialog.findViewById<EditText>(R.id.desc_et)
        val doneButton = dialog.findViewById<Button>(R.id.done_btn)

        doneButton.setOnClickListener {
            val title = titleEt.text.toString()
            val desc = descEt.text.toString()
            val model = ModelDataClass(title, desc)
            viewModel.postPost(model)
        }
    }

    override fun onSuccess(post: ModelDataClass) {
        toast("successsss")
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}