package com.dreampany.frame.ui.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import android.view.Window
import com.afollestad.aesthetic.Aesthetic
import com.dreampany.frame.R
import com.dreampany.frame.data.model.Task
import com.dreampany.frame.data.util.BarUtil
import com.dreampany.frame.ui.fragment.BaseFragment
import dagger.android.support.DaggerAppCompatActivity


/**
 * Created by Hawladar Roman on 5/22/2018.
 * BJIT Group
 * hawladar.roman@bjitgroup.com
 */
abstract class BaseActivityKt : DaggerAppCompatActivity() {

    protected lateinit var binding: ViewDataBinding
    protected var currentTask: Task<*>? = null
    protected var currentFragment: BaseFragment? = null
    protected var fireOnStartUi: Boolean = true

    open fun getLayoutId(): Int {
        return 0
    }

    open fun getToolbarId(): Int {
        return R.id.toolbar
    }

    open fun isFullScreen(): Boolean {
        return false
    }

    open fun isHomeUp(): Boolean {
        return true
    }

    protected abstract fun onStartUi(state: Bundle?)

    protected abstract fun onStopUi()

    override fun onCreate(savedInstanceState: Bundle?) {
        Aesthetic.attach(this)
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        val layoutId = getLayoutId()
        if (layoutId != 0) {
            if (isFullScreen()) {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                BarUtil.hide(this)
            } else {
                BarUtil.show(this)
            }
            binding = DataBindingUtil.setContentView(this, layoutId)
            val toolbar = findViewById<Toolbar>(getToolbarId())
            if (toolbar != null) {

            }
        }
    }
}