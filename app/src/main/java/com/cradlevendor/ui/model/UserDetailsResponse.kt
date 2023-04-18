package com.cradlevendor.ui.model

import com.google.gson.annotations.SerializedName


data class UserDetailsResponse(

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("metaData")
	val metaData: UserMetaData? = null,

	@field:SerializedName("address")
	val address: AddressListItem? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("emailMobile")
	val emailMobile: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("createdOn")
	val createdOn: String? = null,

	@field:SerializedName("status")
	val status: Any? = null
)

data class UserMetaData(


	@field:SerializedName("zipcode")
	val zipcode: String? = null,
	@field:SerializedName("companyName")
	val companyName: String? = null,

	@field:SerializedName("firstName")
	val firstName: String? = null,

	@field:SerializedName("lastName")
	val lastName: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("streetAddress")
	val streetAddress: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("addressType")
	val addressType: Any? = null,

	@field:SerializedName("phoneCode")
	val phoneCode: Any? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("landmark")
	val landmark: String? = null,

	@field:SerializedName("image")
	val image: Any? = null,

	@field:SerializedName("coverImage")
	val coverImage: Any? = null
)

data class Address(

	@field:SerializedName("metaData")
	val metaData: UserMetaData? = null,

	@field:SerializedName("isDefault")
	val isDefault: Boolean? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
