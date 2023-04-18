package com.cradlevendor.ui.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CurrentOrderVendorResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("orderList")
	val orderList: List<OrderListItem?>? = null
)

data class AddressMetaData(

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
	val landmark: String? = null
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
		parcel.readString()
	) {
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
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<AddressMetaData> {
		override fun createFromParcel(parcel: Parcel): AddressMetaData {
			return AddressMetaData(parcel)
		}

		override fun newArray(size: Int): Array<AddressMetaData?> {
			return arrayOfNulls(size)
		}
	}
}

data class ItemsItemVendor(

	@field:SerializedName("itemId")
	val itemId: String? = null,

	@field:SerializedName("metaData")
	val metaData: MetaDataVendor? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("discountPercent")
	val discountPercent: Double? = null,

	@field:SerializedName("productId")
	val productId: String? = null,

	@field:SerializedName("discountedPrice")
	val discountedPrice: Double? = null,

	@field:SerializedName("totalPrice")
	val totalPrice: Double? = null,

	@field:SerializedName("price")
	val price: Double? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("currency")
	val currency: String? = null,

	@field:SerializedName("stock")
	val stock: Int? = null
):Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readParcelable(MetaDataVendor::class.java.classLoader),
		parcel.readValue(Int::class.java.classLoader) as? Int,
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readString(),
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readValue(Double::class.java.classLoader) as? Double,
		parcel.readString(),
		parcel.readString(),
		parcel.readValue(Int::class.java.classLoader) as? Int
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(itemId)
		parcel.writeParcelable(metaData, flags)
		parcel.writeValue(quantity)
		parcel.writeValue(discountPercent)
		parcel.writeString(productId)
		parcel.writeValue(discountedPrice)
		parcel.writeValue(totalPrice)
		parcel.writeValue(price)
		parcel.writeString(name)
		parcel.writeString(currency)
		parcel.writeValue(stock)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ItemsItemVendor> {
		override fun createFromParcel(parcel: Parcel): ItemsItemVendor {
			return ItemsItemVendor(parcel)
		}

		override fun newArray(size: Int): Array<ItemsItemVendor?> {
			return arrayOfNulls(size)
		}
	}
}

data class CartMetaData(

	@field:SerializedName("items")
	val items: List<ItemsItemVendor?>? = null
):Parcelable {
	constructor(parcel: Parcel) : this(parcel.createTypedArrayList(ItemsItemVendor)) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeTypedList(items)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<CartMetaData> {
		override fun createFromParcel(parcel: Parcel): CartMetaData {
			return CartMetaData(parcel)
		}

		override fun newArray(size: Int): Array<CartMetaData?> {
			return arrayOfNulls(size)
		}
	}
}

data class MetaDataVendor(

	@field:SerializedName("images")
	val images: List<String?>? = null,

	@field:SerializedName("description")
	val description: String? = null
):Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.createStringArrayList(),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeStringList(images)
		parcel.writeString(description)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<MetaDataVendor> {
		override fun createFromParcel(parcel: Parcel): MetaDataVendor {
			return MetaDataVendor(parcel)
		}

		override fun newArray(size: Int): Array<MetaDataVendor?> {
			return arrayOfNulls(size)
		}
	}
}

data class OrderListItem(

	@field:SerializedName("orderId")
	val orderId: String? = null,
	@field:SerializedName("createdOn")
	val createdOn: String? = null,

	@field:SerializedName("cartMetaData")
	val cartMetaData: CartMetaData? = null,

	@field:SerializedName("totalPrice")
	val totalPrice: String? = null,

	@field:SerializedName("addressMetaData")
	val addressMetaData: AddressMetaData? = null,

	@field:SerializedName("orderState")
	val orderState: String? = null
):Parcelable {
	constructor(parcel: Parcel) : this(
		parcel.readString(),
		parcel.readString(),
		parcel.readParcelable(CartMetaData::class.java.classLoader),
		parcel.readString(),
		parcel.readParcelable(AddressMetaData::class.java.classLoader),
		parcel.readString()
	) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(orderId)
		parcel.writeString(createdOn)
		parcel.writeParcelable(cartMetaData, flags)
		parcel.writeString(totalPrice)
		parcel.writeParcelable(addressMetaData, flags)
		parcel.writeString(orderState)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<OrderListItem> {
		override fun createFromParcel(parcel: Parcel): OrderListItem {
			return OrderListItem(parcel)
		}

		override fun newArray(size: Int): Array<OrderListItem?> {
			return arrayOfNulls(size)
		}
	}
}
