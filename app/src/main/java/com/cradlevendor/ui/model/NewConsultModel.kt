import com.google.gson.annotations.SerializedName
import java.io.Serializable

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class NewConsultModel (

	@SerializedName("message") val message : String,
	@SerializedName("code") val code : String,
	@SerializedName("frontendmessage") val frontendmessage : String,
	@SerializedName("list") val list : List<NewConsultList>,
	@SerializedName("weeklyRequests") val weeklyRequests : String,
	@SerializedName("acceptedRequests") val acceptedRequests : String
)

data class NewConsultList (

    @SerializedName("age") val age : Int,
    @SerializedName("patientName") val patientName : String,
    @SerializedName("requestDate") val requestDate : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("specialityRequested") val specialityRequested : String,
    @SerializedName("language") val language : String,
    @SerializedName("country") val country : String,
    @SerializedName("message") val message : String,
    @SerializedName("uploadedFiles") val uploadedFiles : String,
    @SerializedName("querId") val querId : String,
    @SerializedName("patientEmail") var patientEmail : String,
    @SerializedName("communicationMode") val communicationMode : String,
    @SerializedName("location") val location : String,
    @SerializedName("profilePhoto") val profilePhoto : String,
    @SerializedName("consultantEmail") val consultantEmail : String,
    @SerializedName("completeDate") val completeDate : String,
    @SerializedName("consultNotes") val consultNotes : String,
    @SerializedName("diagnosis") val diagnosis : String,
    @SerializedName("recommendations") val recommendations : String,
    @SerializedName("tags") val tags : String,
    @SerializedName("documentId") val documentId : String,
    @SerializedName("fcmKeys") val fcmKeys : List<String>,
    @SerializedName("status") val status : String,
    @SerializedName("documentName") val documentName : String
):Serializable