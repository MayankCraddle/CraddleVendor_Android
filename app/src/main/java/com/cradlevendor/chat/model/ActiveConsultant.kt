package com.cradlevendor.chat.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ActiveConsultant (
    @SerializedName("reviews") val reviews:List<ActiveConsultantModel>
)

data class ActiveConsultantModel(
    @SerializedName("queryId") val queryId : String,
    @SerializedName("name") val name : String,
    @SerializedName("consultationMessage") val consultationMessage : String,
    @SerializedName("communicationMode") val communicationMode : String,
    @SerializedName("specialityRequested") val specialityRequested : String,
    @SerializedName("language") val language : String,
    @SerializedName("location") val location : String,
    @SerializedName("profilePhoto") val profilePhoto : String,
    @SerializedName("newDiagnosis") val newDiagnosis : String,
    @SerializedName("medications") val medications : String,
    @SerializedName("tests") val tests : String,
    @SerializedName("status") val status : String,
    @SerializedName("requestDate") val requestDate : String,
    @SerializedName("patientEmail") val patientEmail : String,
    @SerializedName("completeDate") val completeDate : String,
    @SerializedName("consultNotes") val consultNotes : String,
    @SerializedName("diagnosis") val diagnosis : String,
    @SerializedName("recommendations") val recommendations : String,
    @SerializedName("documentId") val documentId : String,
    @SerializedName("documentName") val documentName : String,
    @SerializedName("tags") val tags : String,
    @SerializedName("consultantEmail") val consultantEmail : String,
    @SerializedName("patientName") val patientName : String,
    @SerializedName("patientGender") val patientGender : String,
    @SerializedName("fcmKeys") val fcmKeys : List<String>,
    @SerializedName("age") val age : Int,
    @SerializedName("ratingDone") val isRatingDone : Boolean,
    @SerializedName("country") val country : String
):Serializable