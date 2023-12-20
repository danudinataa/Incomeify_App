package com.incomeify.incomeifyapp.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("data")
	val data: UserData? = null
)

data class UserData(
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null
)
