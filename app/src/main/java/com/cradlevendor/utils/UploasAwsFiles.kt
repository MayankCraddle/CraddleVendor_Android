package com.cradlevendor.utils


class UploadAwsFiles {

    /*companion object {
        fun uploadFile(file: File, context: Context, listener: UploadImageCallBackListener) {
            Log.e("path",file.absolutePath)
            Thread {
                val credentials = BasicAWSCredentials(
                    Constaints.S3ACCESS_KEY,
                    Constaints.S3SECRETACCESS_KEY
                )
                val s3Client = AmazonS3Client(credentials, Region.getRegion(Regions.AP_SOUTH_1))
               try {
                   s3Client.setObjectAcl(
                       Constaints.BUCKET_NAME,
                       Constaints.S3ACCESS_KEY,
                       CannedAccessControlList.PublicRead
                   )
               }catch (e:Exception){
                   e.printStackTrace()
                   listener.onAwsUploadFailure(e.toString())
               }

                runOnUiThread {
                    // s3Client.setRegion(Region.getRegion(Regions.fromName("us-east-2")));
                    val transferUtility = TransferUtility.builder()
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().configuration)
                        .s3Client(s3Client)
                        .build()
                    val fileName = "familyHives"+ Calendar.getInstance().timeInMillis+Math.random()+"."+file.extension
                    val uploadObserver = transferUtility.upload(
                        Constaints.BUCKET_NAME,
                        fileName,
                        file,
                        CannedAccessControlList.PublicRead
                    )

                    uploadObserver.setTransferListener(object : TransferListener {
                        override fun onStateChanged(id: Int, state: TransferState) {
                            if (TransferState.COMPLETED === state) {
                                // Handle a completed download.
                                listener.onAwsUploadSuccess(
                                    fileName,
                                    "https://familyhives-media.s3.ap-south-1.amazonaws.com/" + fileName
                                )
                            }
                        }

                        override fun onProgressChanged(
                            id: Int,
                            bytesCurrent: Long,
                            bytesTotal: Long
                        ) {
                            val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                            val percentDone = percentDonef.toInt()
                            Logger.e("Current: $bytesCurrent Total:$bytesTotal")
                        }

                        override fun onError(id: Int, ex: Exception) {
                            // Handle errors
                            Logger.e(ex.toString())
                            listener.onAwsUploadFailure(ex.toString())
                        }
                        
                    })

                }
            }.start()
        }

        fun uploadFile2(file: File, context: Context, listener: UploadImageCallBackListener) {
            Log.e("path",file.absolutePath)
            Thread {
                val credentials = BasicAWSCredentials(
                    Constaints.S3ACCESS_KEY,
                    Constaints.S3SECRETACCESS_KEY
                )
                val s3Client = AmazonS3Client(credentials, Region.getRegion(Regions.AP_SOUTH_1))
                try {
                    s3Client.setObjectAcl(
                        Constaints.BUCKET_NAME,
                        Constaints.S3ACCESS_KEY,
                        CannedAccessControlList.PublicRead
                    )
                }catch (e:Exception){
                    e.printStackTrace()
                    listener.onAwsUploadFailure(e.toString())
                }

                runOnUiThread {
                    // s3Client.setRegion(Region.getRegion(Regions.fromName("us-east-2")));
                    val fileName = "familyHives"+ Calendar.getInstance().timeInMillis+Math.random()+"."+file.extension
                    val transferUtility = TransferUtility.builder()
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().configuration)
                        .s3Client(s3Client)
                        .build()

                    val uploadObserver = transferUtility.upload(
                        Constaints.BUCKET_NAME,
                        fileName,
                        file,
                        CannedAccessControlList.PublicRead
                    )
//https://familyhives-media.s3.ap-south-1.amazonaws.com/image%253A4759.jpg
                    uploadObserver.setTransferListener(object : TransferListener {
                        override fun onStateChanged(id: Int, state: TransferState) {
                            if (TransferState.COMPLETED === state) {
                                // Handle a completed download.
                                listener.onAwsUploadSuccess(
                                    fileName,
                                    "https://familyhives-media.s3.ap-south-1.amazonaws.com/" + fileName
                                )
                            }
                        }

                        override fun onProgressChanged(
                            id: Int,
                            bytesCurrent: Long,
                            bytesTotal: Long
                        ) {
                            val percentDonef = bytesCurrent.toFloat() / bytesTotal.toFloat() * 100
                            val percentDone = percentDonef.toInt()
                            Logger.e("Current: " + bytesCurrent + " Total:" + bytesTotal)
                        }

                        override fun onError(id: Int, ex: Exception) {
                            // Handle errors
                            Logger.e(ex.toString())
                            ex.message?.let { listener.onAwsUploadFailure(it) }
                        }
                    })

                }
            }.start()

        }

        private fun getInputStream(file: File): InputStream {
            val bitmap = BitmapFactory.decodeFile(file.absolutePath, BitmapFactory.Options())
            val bos = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.JPEG, 30, bos)
            val bitmapdata = bos.toByteArray()
            return ByteArrayInputStream(bitmapdata)
        }
    }*/

}