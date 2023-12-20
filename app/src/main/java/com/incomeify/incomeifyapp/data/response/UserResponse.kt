package com.incomeify.incomeifyapp.data.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null
)

