package com.app.progresviews

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

import com.app.progreswheelview.R

import java.text.DecimalFormat

/**
 * Created by Zeki Guler on 01,February,2016
 * ©2015 Appscore. All Rights Reserved
 */
class ProgressLine : View {

    //Sizes (with defaults)
    private var mBarWidth = 24f
    private var mUnderLineSize = 5f
    private var mValueTextSize = 48f
    private var mDefTextSize = 24f
    private var layoutHeight = 0
    private var layoutWidth = 0

    //Colors (with defaults)
    private var mProgressColor = 0x61b50CCC//Color.GREEN;
    private var mUnderLineColor = Color.GRAY

    //Rectangles
    private val mUnderLineBounds = RectF()
    private val mProgressBounds = RectF()

    //Painy
    private val mValueTextPaint = TextPaint()
    private val mDefTextPaint = TextPaint()
    private val mProgressLinePaint = Paint()
    private val mUnderLinePaint = Paint()


    //Padding (with defaults)
    private val marginTopBars = 30
    private val paddingBetweenText = 20
    private val defaultPaddingLeft = 30
    private val defaultPaddingRight = 30
    private val widthPaddingBetweenBars = 9

    //PAdding (with defaults)
    private val leftPadding: Int = 0
    private val rithPadding: Int = 0


    private var mValueText: String? = "6,0090"
    private var mDefText: String? = "daily steps"
    private var mValueTextWidth: Float = 0.toFloat()

    private var mPercentage = 70
    private var mBarLenght: Int = 0
    private var mScale = 1f

    private val mformatter = DecimalFormat("#,###,###")

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.ProgressLine, defStyle, 0)

        if (a.hasValue(R.styleable.ProgressLine_value))
            mValueText = a.getString(R.styleable.ProgressLine_value)
        if (a.hasValue(R.styleable.ProgressLine_definition))
            mDefText = a.getString(R.styleable.ProgressLine_definition)

        mBarWidth = a.getDimension(R.styleable.ProgressLine_lineBarWidth, mBarWidth)
        mProgressColor = a.getColor(R.styleable.ProgressLine_lineProgressColor, mProgressColor)
        mDefTextSize = a.getDimension(R.styleable.ProgressLine_lineDefTextSize, mDefTextSize)
        mValueTextSize = a.getDimension(R.styleable.ProgressLine_valueDefTextSize, mValueTextSize)
        mPercentage = a.getInt(R.styleable.ProgressLine_valuePercentage, mPercentage)
        mUnderLineSize = a.getDimension(R.styleable.ProgressLine_underLineSize, mUnderLineSize)
        mUnderLineColor = a.getColor(R.styleable.ProgressLine_underLineColor, mUnderLineColor)

        calculateBarScale()

        a.recycle()

        // Update TextPaint and text measurements from attributes
        invalidate()
    }

    private fun calculateBarScale() {
        mScale = if (mPercentage > 100) 1f else mPercentage * 0.01f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        layoutWidth = w
        layoutHeight = h
        setupBounds()
        setupPaints()

        invalidate()
    }

    private fun setupPaints() {
        mValueTextPaint.color = Color.BLACK
        mValueTextPaint.flags = Paint.ANTI_ALIAS_FLAG

        mDefTextPaint.color = Color.BLACK
        mDefTextPaint.flags = Paint.ANTI_ALIAS_FLAG


        mProgressLinePaint.color = mProgressColor
        mProgressLinePaint.isAntiAlias = true
        mProgressLinePaint.style = Paint.Style.FILL
        mProgressLinePaint.strokeWidth = mBarWidth

        mUnderLinePaint.color = mUnderLineColor
        mUnderLinePaint.isAntiAlias = true
        mUnderLinePaint.style = Paint.Style.FILL
        mUnderLinePaint.strokeWidth = mUnderLineSize


        mBarLenght = this.width - defaultPaddingLeft - defaultPaddingRight - paddingLeft - paddingRight

    }

    private fun setupBounds() {

        // Count number text
        mValueTextPaint.textSize = mValueTextSize
        mValueTextWidth = mValueTextPaint.measureText(mValueText)


        // Definition text
        mDefTextPaint.textSize = mDefTextSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val startX_V = (paddingLeft + defaultPaddingLeft).toFloat()
        canvas.drawText(mValueText!!,
                startX_V,
                (this.height / 2).toFloat(),
                mValueTextPaint
        )

        val start_D = startX_V + mValueTextWidth + paddingBetweenText.toFloat()
        canvas.drawText(mDefText!!,
                start_D,
                (this.height / 2).toFloat(),
                mDefTextPaint
        )

        val startX_P = (paddingLeft + defaultPaddingLeft).toFloat()
        val endX_P = startX_P + mBarLenght * mScale
        canvas.drawLine(
                startX_P,
                (this.height / 2 + marginTopBars).toFloat(),
                endX_P,
                (this.height / 2 + marginTopBars).toFloat(),
                mProgressLinePaint)

        val startXU = endX_P + widthPaddingBetweenBars
        val endXU = startXU + mBarLenght * (1 - mScale)
        canvas.drawLine(
                startXU,
                (this.height / 2).toFloat() + mBarWidth / 2 + marginTopBars.toFloat(),
                endXU,
                (this.height / 2).toFloat() + mBarWidth / 2 + marginTopBars.toFloat(),
                mUnderLinePaint)
    }

    fun setmDefText(mDefText: String) {
        this.mDefText = mDefText
        invalidate()
    }

    fun setmValueText(value: Int) {
        this.mValueText = mformatter.format(value.toLong())
        setupBounds()
        setupPaints()
        invalidate()
    }

    fun setmValueText(value: String) {
        this.mValueText = value
        setupBounds()
        setupPaints()
        invalidate()
    }

    fun setmPercentage(percetage: Int) {

        val diff = percetage - mPercentage
        ValueAnimator()
        val valueAnimator = ValueAnimator
                .ofInt(mPercentage, mPercentage + diff)
                .setDuration(1000)
        valueAnimator.addUpdateListener { animation ->
            mPercentage = animation.animatedValue as Int
            setupBounds()
            setupPaints()
            calculateBarScale()
            invalidate()
        }
        valueAnimator.start()
    }
}
