package com.cradlevendor.ui.model

import com.google.gson.annotations.SerializedName

data class NewStateListResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("list")
	val list: List<String?>? = null
)
