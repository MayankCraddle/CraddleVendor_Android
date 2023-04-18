package com.cradlevendor.ui.model

import com.google.gson.annotations.SerializedName

data class NotificationListResponse(

	@field:SerializedName("notificationList")
	val notificationList: List<NotificationListItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("totalCount")
	val totalCount: Int? = null
)

data class NotificationListItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("body")
	val body: String? = null
)
