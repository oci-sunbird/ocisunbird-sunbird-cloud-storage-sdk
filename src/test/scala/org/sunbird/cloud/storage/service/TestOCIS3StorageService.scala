package org.sunbird.cloud.storage.service

import org.scalatest.FlatSpec
import org.scalatest._
import org.sunbird.cloud.storage.Model.Blob
import org.sunbird.cloud.storage.conf.AppConf
import org.sunbird.cloud.storage.factory.{StorageConfig, StorageServiceFactory}
import org.sunbird.cloud.storage.exception.StorageServiceException
import java.nio.file.{Files, Paths}
class TestOCIS3StorageService extends FlatSpec with Matchers {

//     it should "Test for oci s3 storage - Upload a folder to cloud" in {

//         val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"),AppConf.getStorageEndpoint("oci"),AppConf.getRegion("oci")))
//         val storageContainer = AppConf.getConfig("oci_storage_container")
//         val uploaded_obj = s3Service.upload(storageContainer, "src/test/resources/1234", "resources/", Option(true),Option(1), Option(2), None)
//         assert(uploaded_obj.contains("https://"))
//         s3Service.closeContext()
//     }

//     it should "Test for oci s3 storage - Upload a file to cloud" in {

//         val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"),AppConf.getStorageEndpoint("oci"),AppConf.getRegion("oci")))
//         val storageContainer = AppConf.getConfig("oci_storage_container")
//         val uploaded_obj = s3Service.upload(storageContainer, "src/test/resources/test-data.log", "testUpload/2.log", Option(false),Option(1), Option(2), None)
//         assert(uploaded_obj.contains("https://"))
//         s3Service.closeContext()
//     }

//    it should "Test for oci s3 storage - List objects from cloud storage for a given prefix" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val list_objs = s3Service.listObjects(storageContainer, "testUpload/" ,Option(true))
//     //    list_objs.foreach { println }
//     //    list_objs.foreach(c => println(c.metadata))
//     //    list_objs.foreach{
//     //      obj => print(obj.metadata.get("name"))
//     //    }
//        assert(list_objs.isInstanceOf[List[Blob]] || list_objs.isEmpty)
//        s3Service.closeContext()
//    }

//    it should "Test for oci s3 storage - List object keys from cloud storage for a given prefix" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val list_objs_keys = s3Service.listObjectKeys(storageContainer, "testUpload/")
//        assert(list_objs_keys.isInstanceOf[List[String]] || list_objs_keys.isEmpty)
//        s3Service.closeContext()
//    }


//    it should "Test for oci s3 storage - Get the blob object details" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val oss_object = s3Service.getObject(storageContainer, "testUpload/1.log",Option(true))
//        assert(oss_object.isInstanceOf[Blob])
//        s3Service.closeContext()
//    }

//    it should "Test for oci s3 storage - Search for objects for a given prefix" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val list_objs = s3Service.searchObjects(storageContainer, "testUpload/")
//        assert(list_objs.isInstanceOf[List[Blob]] || list_objs.isEmpty)
//        s3Service.closeContext()
//    }

//    it should "Test for oci s3 storage - Search for objects keys for a given prefix and return only keys" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val list_objs_keys = s3Service.searchObjectkeys(storageContainer, "testUpload/")
//        assert(list_objs_keys.isInstanceOf[List[String]] || list_objs_keys.isEmpty)
//        s3Service.closeContext()
//    }   

//    it should "Test for oci s3 storage - Get HDFS compatible file paths to be used in tech stack like Spark" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val list_objs = s3Service.listObjects(storageContainer, "testUpload/" ,Option(true))
//        val hdfs_paths = s3Service.getPaths(storageContainer, list_objs)
//        assert(hdfs_paths.isInstanceOf[List[String]] || hdfs_paths.isEmpty)
//        s3Service.closeContext()
//    }  

//    it should "Test for oci s3 storage - Get the blob object data" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val obj_data = s3Service.getObjectData(storageContainer, "testUpload/1.log")
//        assert(obj_data.isInstanceOf[Array[String]] || obj_data.isEmpty)
//        s3Service.closeContext()
//    }  

//    it should "Test for oci s3 storage - Get the URI of the given prefix" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val obj_uri = s3Service.getUri(storageContainer, "testUpload/")
//        assert(obj_uri.isInstanceOf[String])
//        s3Service.closeContext()
//    } 


//    it should "Test for oci s3 storage - Put a blob in the cloud with the given content data" in {

//        val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
//        val storageContainer = AppConf.getConfig("oci_storage_container")
//        val content_array = Files.readAllBytes(Paths.get("src/test/resources/test-data.log"))
//        val obj_put_str = s3Service.put(storageContainer, content_array,"testUpload/3.log")
//        println(obj_put_str)
//        assert(obj_put_str.isInstanceOf[String])
//        s3Service.closeContext()
//    } 

   it should "Test for oci s3 storage - Get pre-signed URL to access an object in the cloud store." in {

       val s3Service = StorageServiceFactory.getStorageService(StorageConfig("oci", AppConf.getStorageKey("oci"), AppConf.getStorageSecret("oci"), AppConf.getStorageEndpoint("oci"), AppConf.getRegion("oci")))
       val storageContainer = AppConf.getConfig("oci_storage_container")
       val obj_uri = s3Service.getSignedURL(storageContainer, "testUpload/")
       assert(obj_uri.isInstanceOf[String])
       s3Service.closeContext()
   }
   
}
