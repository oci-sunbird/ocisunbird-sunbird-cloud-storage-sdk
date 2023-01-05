package org.sunbird.cloud.storage.service

import org.jclouds.blobstore._
import com.google.common.io._
import java.io._
import com.google.common.collect._

import org.apache.commons.io.FilenameUtils
import org.apache.tika.Tika
import org.sunbird.cloud.storage.exception.StorageServiceException
import org.sunbird.cloud.storage.util.{CommonUtil, JSONUtils}

import collection.JavaConverters._
import org.jclouds.blobstore.options.ListContainerOptions.Builder.{afterMarker, prefix, recursive}
import org.sunbird.cloud.storage.Model.Blob
import org.jclouds.blobstore.options.{CopyOptions, PutOptions}
import org.sunbird.cloud.storage.conf.AppConf

import scala.collection.mutable.ListBuffer
import scala.concurrent.{ExecutionContext, Future}

import com.google.inject.Module
import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStoreContext
import org.sunbird.cloud.storage.BaseStorageService
import org.sunbird.cloud.storage.factory.StorageConfig



// Below can be removed once ready for production
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule
import org.jclouds.logging.slf4j.SLF4JLogger
//////////////////////////////////////////////////

import java.util.Properties

class OCIS3StorageService(config: StorageConfig) extends BaseStorageService {

    val overrides = new Properties()
    val modules = ImmutableSet.of(new SLF4JLoggingModule().asInstanceOf[Module])

    overrides.setProperty("jclouds.provider", "s3")
    overrides.setProperty("jclouds.endpoint", config.endPoint.get)
    overrides.setProperty("jclouds.s3.virtual-host-buckets", "false")
    overrides.setProperty("jclouds.strip-expect-header", "true")
    overrides.setProperty("jclouds.regions", config.region)
    overrides.setProperty("jclouds.s3.signer-version", "4")

    var context = ContextBuilder.newBuilder("s3").endpoint(config.endPoint.get).overrides(overrides).modules(modules).credentials(config.storageKey, config.storageSecret).buildView(classOf[BlobStoreContext])
    var blobStore = context.getBlobStore

    override def getPaths(container: String, objects: List[Blob]): List[String] = {
        objects.map{f => "s3n://" + container + "/" + f.key}
    } 

    override def upload(container: String, file: String, objectKey: String, isDirectory: Option[Boolean] = Option(false), attempt: Option[Int] = Option(1), retryCount: Option[Int] = None, ttl: Option[Int] = None): String = {
        try {
            if(isDirectory.get) {
                val d = new File(file)
                val files = filesList(d)
                val list = files.map {f =>
                    val key = objectKey + f.getAbsolutePath.split(d.getAbsolutePath + File.separator).last
                    upload(container, f.getAbsolutePath, key, Option(false), attempt, retryCount, ttl)
                }
                list.mkString(",")
            }
            else {
                if (attempt.getOrElse(1) >= retryCount.getOrElse(maxRetries)) {
                    val message = s"Failed to upload. file: $file, key: $objectKey, attempt: $attempt, maxAttempts: $retryCount. Exceeded maximum number of retries"
                    throw new StorageServiceException(message)
                }

                 blobStore.createContainerInLocation(null, container)
                val fileObj = new File(file)
                val payload = Files.asByteSource(fileObj)
                val payload_size = payload.size()
                val  contentType = tika.detect(fileObj)
                println("*** About to BuildBlob****")
                println(blobStore.getClass)
//                var temp = blobStore.blobBuilder(objectKey).payload(payload).contentType(contentType).contentType("UTF-8").contentLength(payload_size)
//                println(temp.getClass)
//                val blob = blobStore.blobBuilder(objectKey).payload(payload).contentType(contentType).contentType("UTF-8").contentLength(payload.size()).build()
//                println(blobStore.blobBuilder(objectKey).payload(payload).contentType(contentType).getClass)
                 val blob = blobStore.blobBuilder(objectKey).payload(payload).contentType(contentType).contentEncoding("UTF-8").contentLength(payload_size).build()
//                val blob = blobStore.blobBuilder(objectKey).payload(payload).contentType(contentType).contentEncoding("UTF-8").build()
                println(blobStore.blobBuilder(objectKey).payload(payload).contentType(contentType).contentEncoding("UTF-8").getClass)
                println("*** About to putBlob****")
                blobStore.putBlob(container, blob, new PutOptions().multipart())
//                 blobStore.putBlob(container, blob)
                if (ttl.isDefined) {
                    getSignedURL(container, objectKey, Option(ttl.get))
                } else
                    blobStore.blobMetadata(container, objectKey).getUri.toString
            }
        }
        catch {
            case e: Exception => {
                e.printStackTrace()
                Thread.sleep(attempt.getOrElse(1)*2000)
                val uploadAttempt = attempt.getOrElse(1) + 1
                if (uploadAttempt <= retryCount.getOrElse(maxRetries)) {
                    upload(container, file, objectKey, isDirectory, Option(uploadAttempt), retryCount, ttl)
                } else {
                    throw e;
                }
            }
        }
    }



}