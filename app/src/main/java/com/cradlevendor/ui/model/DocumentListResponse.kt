package com.cradlevendor.ui.model

import com.google.gson.annotations.SerializedName

data class DocumentListResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("list")
	val list: List<ListItem?>? = null
)

data class ListItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
