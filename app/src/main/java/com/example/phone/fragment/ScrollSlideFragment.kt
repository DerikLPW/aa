package com.example.phone.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phone.ContainerActivity
import com.example.phone.R
import com.example.phone.bean.Detail
import com.example.phone.bean.FirstBean
import com.example.phone.bean.ResultD
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_first.*


class ScrollSlideFragment : Fragment() {
    private var mActivity: ContainerActivity? = null
    private var mFirstBean: FirstBean? = null
    private var recyclerLeft: RecyclerView? = null
    private var recyclerRight: RecyclerView? = null

    companion object {
        @JvmStatic
        fun newInstance() =
            ScrollSlideFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }

        val inputStream = context?.assets?.open("data.json")
        val readBytes = inputStream?.readBytes()
        readBytes?.let {
            val json = String(it)
            mFirstBean = Gson().fromJson(json, FirstBean::class.java)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        recyclerLeft = view.findViewById(R.id.recycler_view_left)
        recyclerRight = view.findViewById(R.id.recycler_view_right)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val firstBean = mFirstBean
        val ctx = context

        if (firstBean != null && ctx != null) {
            val moreData: MutableList<ResultD> = mutableListOf()
            repeat(100000) {
                moreData.addAll(firstBean.resultD)
            }
            Log.d(
                "mAdapter",
                "moreData size ${moreData.size}, detail size=${moreData[0].detail.size}"
            )
            recyclerLeft?.adapter = LeftAdapter(ctx, moreData)
            recyclerLeft?.layoutManager = LinearLayoutManager(ctx)

            recyclerRight?.adapter = RightAdapter(ctx, moreData)
            recyclerRight?.layoutManager = LinearLayoutManager(ctx)

            recyclerLeft?.addOnScrollListener(listenerLeft)
            recyclerRight?.addOnScrollListener(listenerRight)

            recyclerLeft?.addOnItemTouchListener(itemTouchListenerLeft)
            recyclerRight?.addOnItemTouchListener(itemTouchListenerRight)

            csv_first.setScrollViewListener { _, _, _, _, _ ->
                recyclerLeft?.stopScroll()
                recyclerRight?.stopScroll()
            }
        }
    }

    private var itemTouchListenerLeft = object : RecyclerView.OnItemTouchListener {

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            if (recyclerRight?.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                recyclerRight?.stopScroll()
            }
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }

    private var itemTouchListenerRight = object : RecyclerView.OnItemTouchListener {

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            if (recyclerLeft?.scrollState != RecyclerView.SCROLL_STATE_IDLE) {
                recyclerLeft?.stopScroll()
            }
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

        }
    }

    private var listenerLeft = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.scrollState) {
                recyclerRight?.scrollBy(dx, dy)
            }
        }
    }

    private var listenerRight = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (RecyclerView.SCROLL_STATE_IDLE != recyclerView.scrollState) {
                recyclerLeft?.scrollBy(dx, dy)
            }
        }
    }

    class LeftAdapter(private val context: Context, private val list: List<ResultD>) :
        RecyclerView.Adapter<LeftViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeftViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.adapter_item_left, parent, false)
            return LeftViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: LeftViewHolder, position: Int) {
            try {
                holder.apply {
                    list[position].let {
                        tvItemTitle.text = it.title
                        tvCode.text = it.code
                        tvStatus.text = it.status
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class LeftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemTitle: TextView = itemView.findViewById(R.id.tv_first_title)
        val tvCode: TextView = itemView.findViewById(R.id.tv_first_code)
        val tvStatus: TextView = itemView.findViewById(R.id.tv_first_status)
    }

    class RightAdapter(private val context: Context, private val list: List<ResultD>) :
        RecyclerView.Adapter<RightViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RightViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.adapter_item_right, parent, false)
            return RightViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: RightViewHolder, position: Int) {
            try {
                holder.apply {
                    list[position].let {
                        recyclerViewRightItem.adapter =
                            RightItemAdapter(itemView.context, it.detail)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    class RightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerViewRightItem: RecyclerView =
            itemView.findViewById(R.id.recycler_view_right_item)

        init {
            recyclerViewRightItem.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
        }
    }

    class RightItemAdapter(private val context: Context, private val list: List<Detail>) :
        RecyclerView.Adapter<RightItemAdapter.FirstRightViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstRightViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_item_right_dessc, parent, false)
            return FirstRightViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: FirstRightViewHolder, position: Int) {
            try {
                holder.apply {
                    list[position].let {
                        tvRightItem.text = it.value
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        inner class FirstRightViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvRightItem: TextView = itemView.findViewById(R.id.tv_right_item)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ContainerActivity) {
            mActivity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mActivity = null
    }


}
