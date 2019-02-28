package com.aspire.sunset

import android.support.v4.app.Fragment

class SunsetActivity : SingleFragmentActivity() {

    override fun createFragment(): Fragment {
        return SunsetFragment.newInstance()
    }
}
