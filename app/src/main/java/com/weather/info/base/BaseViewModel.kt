package com.weather.info.base

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

/**
 * Class which will be extended by every ViewModels
 */
abstract class BaseViewModel : ViewModel(), Observable {

    /**
     * Callback for any propertyChanges
     * */
    @Transient
    private var transientCallbacks: PropertyChangeRegistry? = null

    /**
     * Notifies listeners that instance have added.
     */
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (transientCallbacks == null) {
                transientCallbacks = PropertyChangeRegistry()
            }
        }
        transientCallbacks?.add(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have removed.
     */
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        synchronized(this) {
            if (transientCallbacks == null) {
                return
            }
        }
        transientCallbacks?.remove(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    @Suppress("unused")
    fun notifyChange() {
        synchronized(this) {
            if (transientCallbacks == null) {
                return
            }
        }
        transientCallbacks?.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with Bind able to generate a field in
     * `BR` to be used as `fieldId`.

     * @param fieldId The generated BR id for the Bind able field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        synchronized(this) {
            if (transientCallbacks == null) {
                return
            }
        }
        transientCallbacks?.notifyCallbacks(this, fieldId, null)
    }
}