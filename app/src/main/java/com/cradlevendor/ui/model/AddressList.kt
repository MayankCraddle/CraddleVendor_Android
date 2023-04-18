package com.cradlevendor.ui.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AddressList(

	@field:SerializedName("addressList")
	val addressList: List<AddressListItem?>? = null
)

data class MetaData(

	@field:SerializedName("zipcode")
	val zipcode: String? = null,

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

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("landmark")
	val landmark: String? = null,
	@field:SerializedName("addressType")
	val addressType: String? = null
):Parcelable {
	constructor(parcel: Parcel) : this(
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),parcel.readString()) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(zipcode)
		parcel.writeString(firstName)
		parcel.writeString(lastName)
		parcel.writeString(country)
		parcel.writeString(phone)
		parcel.writeString(streetAddress)
		parcel.writeString(city)
		parcel.writeString(state)
		parcel.writeString(landmark)
		parcel.writeString(addressType)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<MetaData> {
		override fun createFromParcel(parcel: Parcel): MetaData {
			return MetaData(parcel)
		}

		override fun newArray(size: Int): Array<MetaData?> {
			return arrayOfNulls(size)
		}
	}
}

data class AddressListItem(

	@field:SerializedName("metaData")
	val metaData: MetaData? = null,

	@field:SerializedName("isDefault")
	val isDefault: Boolean? = null,

	@field:SerializedName("id")
	val id: String? = null
):Parcelable {
	constructor(parcel: Parcel) : this(
			parcel.readParcelable(MetaData::class.java.classLoader),
			parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
			parcel.readString()) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeParcelable(metaData, flags)
		parcel.writeValue(isDefault)
		parcel.writeString(id)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<AddressListItem> {
		override fun createFromParcel(parcel: Parcel): AddressListItem {
			return AddressListItem(parcel)
		}

		override fun newArray(size: Int): Array<AddressListItem?> {
			return arrayOfNulls(size)
		}
	}
}
