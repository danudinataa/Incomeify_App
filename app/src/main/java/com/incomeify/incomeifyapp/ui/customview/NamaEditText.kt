package com.incomeify.incomeifyapp.ui.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher

class NamaEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                if (!isNameValid(s.toString())) {
                    error = "Invalid name"
                }
            }
        })
    }

    private fun isNameValid(name: CharSequence?): Boolean {
        return name != null && name.trim().isNotEmpty()
    }
}
