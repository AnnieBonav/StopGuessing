package com.anniebonav.stopguessingm3

import android.widget.EditText
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UIViewModelAddMealPlan : ViewModel() {

    val currentMealPlanName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    //@Bindable
    val editTextContent = MutableLiveData<String>()


    /*
    val _currentMealPlanName: LiveData<String>
        get() = _currentMealPlanName*/

    /*
    @Bindable
    val editTextContent: MutableLiveData<String>()

    private val _displayedEditTextContnt = MutableLiveData<String>()
    val displayedEditTextContent: LiveData<String>
    get() = _displayedEditTextContnt*/

}