package org.sunbird.cloud.storage.service

import org.scalatest.FlatSpec
import org.scalatest._
import org.sunbird.cloud.storage.conf.AppConf
import org.sunbird.cloud.storage.factory.{StorageConfig, StorageServiceFactory}
import org.sunbird.cloud.storage.exception.StorageServiceException
class TestOCIS3StorageService extends FlatSpec with Matchers {

    it should "Test for oci s3 storage upload file" in {

        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"),AppConf.getStorageEndpoint("oci"),AppConf.getRegion("oci")))
        val storageContainer = AppConf.getConfig("oci_storage_container")
        val uploaded_obj = s3Service.upload(storageContainer, "src/test/resources/test-data.log", "testUpload/2.log", Option(false),Option(1), Option(2), None)
        assert(uploaded_obj.contains("https://"))
        s3Service.closeContext()
    }

//    it should "Test for oci s3 storage list file" in {
//
//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val uploaded_obj = s3Service.listObjects(storageContainer, "testUpload/")
//        assert(uploaded_obj.contains("https://"))
//        s3Service.closeContext()
//    }
}
