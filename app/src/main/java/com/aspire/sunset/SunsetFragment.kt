package com.aspire.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator

/**
 * Created by thomas on 11/15/17.
 */

class SunsetFragment : Fragment() {
    private lateinit var sceneView: View
    private lateinit var sunView: View
    private lateinit var skyView: View

    private var blueSkyColor: Int = 0
    private var sunsetSkyColor: Int = 0
    private var nightSkyColor: Int = 0

    private var sunIsUp = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_sunset, container, false)

        sceneView = view
        sunView = view.findViewById(R.id.sun)
        skyView = view.findViewById(R.id.sky)

        val resources = resources
        blueSkyColor = resources.getColor(R.color.blue_sky)
        sunsetSkyColor = resources.getColor(R.color.sunset_sky)
        nightSkyColor = resources.getColor(R.color.night_sky)

        sceneView.setOnClickListener {
            if (sunIsUp) {
                startSunsetAnimation()
            } else {
                startSunriseAnimation()
            }
        }
        return view
    }

    private fun startSunsetAnimation() {
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()

        val heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunYStart, sunYEnd)
                .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
                .setDuration(3000)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
                .setDuration(1500)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        val animatorSet = AnimatorSet()
        animatorSet
                .play(heightAnimator)
                .with(sunsetSkyAnimator)
                .before(nightSkyAnimator)
        animatorSet.start()
        sunIsUp = false
    }

    private fun startSunriseAnimation() {
        val sunYEnd = sunView.top.toFloat()
        val sunYStart = skyView.height.toFloat()

        val heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunYStart, sunYEnd)
                .setDuration(3000)
        heightAnimator.interpolator = AccelerateInterpolator()

        val sunriseSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", nightSkyColor, sunsetSkyColor)
                .setDuration(3000)
        sunriseSkyAnimator.setEvaluator(ArgbEvaluator())

        val morningSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", sunsetSkyColor, blueSkyColor)
                .setDuration(1500)
        morningSkyAnimator.setEvaluator(ArgbEvaluator())

        val animatorSet = AnimatorSet()
        animatorSet
                .play(heightAnimator)
                .with(sunriseSkyAnimator)
                .before(morningSkyAnimator)
        animatorSet.start()
        sunIsUp = true
    }

    companion object {
        fun newInstance(): SunsetFragment {
            return SunsetFragment()
        }
    }
}
