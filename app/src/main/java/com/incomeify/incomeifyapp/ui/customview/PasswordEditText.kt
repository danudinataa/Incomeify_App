package com.incomeify.incomeifyapp.ui.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

class PasswordEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        transformationMethod = PasswordTransformationMethod.getInstance()

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                validatePassword(s.toString())
            }
        })
    }

    private fun validatePassword(password: String) {
        error = when {
            password.isEmpty() -> "Password cannot be empty"
            password.length < 8 -> "Password must be at least 8 characters"

            else -> null
        }
    }

}
