package com.cradlevendor.ui.model

import com.google.gson.annotations.SerializedName

data class AccountDetailListResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("accountDetailList")
	val accountDetailList: List<AccountDetailListItem?>? = null
)

data class AccountMetadata(

	@field:SerializedName("accountName")
	val accountName: String? = null,

	@field:SerializedName("bankSortCode")
	val bankSortCode: String? = null,

	@field:SerializedName("accountNumber")
	val accountNumber: String? = null
)

data class AccountDetailListItem(

	@field:SerializedName("isDefault")
	val isDefault: Boolean? = null,

	@field:SerializedName("accountMetadata")
	val accountMetadata: AccountMetadata? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
