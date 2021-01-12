package com.example.phone.fragment

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phone.ContainerActivity

import com.example.phone.R
import kotlinx.android.synthetic.main.fragment_line_chart.*


class LineChartFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var mActivity: ContainerActivity? = null

    companion object {
        @JvmStatic
        fun newInstance() =
            LineChartFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_line_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lcv.bgColor = Color.GRAY
        lcv.items = floatArrayOf(
            60f,
            100f,
            0f,
            23f,
            170f,
            50f,
            10f
        )
        lcv.setOnClickListener{
            println("lcv clicked")
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
