package com.example.phone.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.min


class LineChartView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {

    //绘制折线画笔
    private var mPaintLine: Paint = Paint()

    //绘制渐变效果画笔
    private val mPaintShader: Paint = Paint()

    //渐变颜色
    private var shadeColors: IntArray

    //渐变Shader
    private var mShader: Shader? = null

    //折线四周的内间距，不能太大
    private var mPadding: Float = 20f

    //控件宽度
    private var mWidth: Int = 300

    //控件高度
    private var mHeight: Int = 180

    //折线宽度
    private var mAxesWidth: Float = 3f

    //点之间在X轴的间隔
    private var xInternal: Float = 1f

    //点之间在Y轴的间隔
    private var yInternal: Float = 1f

    //X轴的起始坐标，点的X坐标在此值基础上计算
    private var xStartPoint: Float = 0f

    //Y轴的起始坐标，点的Y坐标在此值基础上计算
    private var yStartPoint: Float = 0f

    //用于表示Y轴高度，数据不能超过最大值或小于最小值，可设置
    var maxValue: Float = 140f
    var minValue: Float = 0f

    var bgColor = Color.WHITE

    //数据需要在min~max范围内
    var items: FloatArray? = null

    //折线的路径
    private val mPathLine = Path()

    //阴影的路径，需调用close进行闭合
    private val mPathShadow = Path()

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    init {
        mPaintLine.color = Color.BLUE // 画笔颜色 - 黑色
        mPaintLine.style = Paint.Style.STROKE // 描边模式
        mPaintLine.strokeWidth = mAxesWidth // 边框宽度 - 10
        mPaintLine.isAntiAlias = true

        mPaintShader.isAntiAlias = true
        mPaintShader.style = Paint.Style.FILL // 填充模式

        //阴影渐变颜色
        shadeColors = intArrayOf(
            Color.argb(100, 255, 86, 86),
            Color.argb(15, 255, 86, 86)
        )
    }

    /**
     * 设置阴影渐变颜色
     */
    public fun setShadeColors(colors: IntArray) {
        shadeColors = colors
        //置空原Shader
        mShader = null
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = getWidthSize(widthMeasureSpec)
        mHeight = getHeightSize(heightMeasureSpec)

        setMeasuredDimension(mWidth, mHeight)

        items?.let {
            if (it.isNotEmpty()) {
                //View的原点(0,0)在左上角, 图形左下角的起始值坐标需要计算
                xStartPoint = mPadding //X轴从内间距后开始
                yStartPoint = mHeight - mPadding //Y轴从高度减掉下方内间距后开始

                //将X轴可用宽度按数据个数划分
                xInternal = if (it.size == 1) 0f else (mWidth - 2 * mPadding) / (it.size - 1)
                //将数据最大区间值按可用高度进行划分，即每单位高度可表示多少值
                yInternal = (maxValue - minValue) / (mHeight - 2 * mPadding)
            }
        }
    }

    private fun getWidthSize(widthMeasureSpec: Int): Int {
        val size = MeasureSpec.getSize(widthMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        return if (mode == MeasureSpec.EXACTLY) size else mWidth
    }

    private fun getHeightSize(heightMeasureSpec: Int): Int {
        val size = MeasureSpec.getSize(heightMeasureSpec)
        val mode = MeasureSpec.getMode(heightMeasureSpec)
        return if (mode == MeasureSpec.EXACTLY) size else mHeight
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        //  设置背景色
        setBackgroundColor(bgColor)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        items?.let {
            if (it.isNotEmpty()) {
                for ((index, value) in it.withIndex()) {
                    //点的x坐标
                    val x = index * xInternal + xStartPoint
                    //点的y坐标
                    val finalValue = max(min(value, maxValue), minValue)
                    //yStartPoint为View坐标系的Y轴最大可用坐标值（排除了padding），并为所要绘制图形的假想起点坐标。
                    val y = yStartPoint - (finalValue - minValue) / yInternal
                    //或者
//            val y = mHeight - (mPadding + (finalValue - minValue) / yInternal)
                    if (index == 0) {
                        mPathLine.moveTo(xStartPoint, y)
                        mPathShadow.moveTo(xStartPoint, y)
                    } else {
                        mPathLine.lineTo(x, y)
                        mPathShadow.lineTo(x, y)
                        if (index == it.size - 1) {
                            mPathShadow.lineTo(x, yStartPoint)
                            mPathShadow.lineTo(xStartPoint, yStartPoint)
                            mPathShadow.close()
                        }
                    }
                }

                if (mShader == null) {
                    mShader = LinearGradient(
                        mPadding,
                        mPadding,
                        mPadding,
                        height.toFloat(),
                        shadeColors,
                        null,
                        Shader.TileMode.CLAMP
                    )
                }
                mPaintShader.shader = mShader
                canvas?.drawPath(mPathShadow, mPaintShader)
                canvas?.drawPath(mPathLine, mPaintLine)
            }
        }
    }

}