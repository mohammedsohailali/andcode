package com.weather.info.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.weather.info.di.Injectable
import com.weather.info.util.PrefUtils
import javax.inject.Inject

/**
 * Class which will be extended by every Activity
 */
@Suppress("unused")
abstract class BaseActivity : AppCompatActivity(), Injectable {

    /**
     * Object for @see [PrefUtils] for preferences storage
     * */
    @Inject
    lateinit var prefUtils: PrefUtils

    /**
     * Object for @see [ViewModelProvider.Factory]
     * */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory



    /**
     * To get Layout Resource ID
     * @return Layout ID
     */
    abstract fun layoutId(): Int

    /**
     * this method gets called when this activity gets created
     * Annotated @CallSuper to make mandatory super call to child class
     * */
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    /**
     * One of the methods of Activity Life Cycle, this will be called after activity gets created
     * */
    override fun onResume() {
        super.onResume()
    }

    /**
     * this method is used for hiding the keyboard
     * */
    fun hideKeyboard() {
        val view = findViewById<View>(android.R.id.content)
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    fun showProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar(progressBar: ProgressBar) {
        progressBar.visibility = View.GONE
    }

}