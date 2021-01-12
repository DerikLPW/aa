package com.example.phone.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phone.R
import com.example.phone.anim.DrawableAnim

class DrawableAnimFragment : Fragment() {

    private var mList: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
        mList.add("test1")
        mList.add("test2")
        mList.add("test3")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawable_anim, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        val adapter = MyAdapter()
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                startAnim(view, resources.getDrawable(R.drawable.selector_item_bg))
            }
        })
    }

    inner class MyAdapter : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        private var listener: OnItemClickListener? = null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val view: View = layoutInflater.inflate(R.layout.adapter_item_drawable_amin, parent, false)
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.cciItem.text = mList[position]
        }

        override fun getItemCount(): Int {
            return mList.size
        }

        inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
            val cciItem: TextView = itemView.findViewById(R.id.tv_item)

            init {
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View) {
                if (listener != null) {
                    listener!!.onItemClick(v, this.adapterPosition)
                }
            }
        }

        fun setOnItemClickListener(listener: OnItemClickListener?) {
            this.listener = listener
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    private fun startAnim(view: View, drawable: Drawable) {
        val drawableAnim = DrawableAnim(view, drawable)
        drawableAnim.duration = 2000
        view.startAnimation(drawableAnim)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                DrawableAnimFragment().apply {
                }
    }

}