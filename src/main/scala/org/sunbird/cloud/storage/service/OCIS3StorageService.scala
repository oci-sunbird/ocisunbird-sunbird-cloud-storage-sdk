package org.sunbird.cloud.storage.service

import org.jclouds.ContextBuilder
import org.jclouds.blobstore.BlobStoreContext
import org.sunbird.cloud.storage.BaseStorageService
import org.sunbird.cloud.storage.Model.Blob
import org.sunbird.cloud.storage.factory.StorageConfig
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule
import java.io.File
import java.util.Properties

class OCIS3StorageService(config: StorageConfig) extends BaseStorageService {

    val overrides = new Properties()
    Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule())

    overrides.setProperty("jclouds.provider", "s3")
    overrides.setProperty("jclouds.endpoint", "https://apaccpt03.compat.objectstorage.us-ashburn-1.oraclecloud.com")
    overrides.setProperty("jclouds.s3.virtual-host-buckets", "false")
    overrides.setProperty("jclouds.strip-expect-header", "true")
        // var context = ContextBuilder.newBuilder("s3").endpoint(config.endPoint.get).credentials(config.storageKey, config.storageSecret).buildView(classOf[BlobStoreContext])
    // var context = ContextBuilder.newBuilder("s3").endpoint(config.endPoint.get).credentials(config.storageKey, config.storageSecret).buildView(classOf[BlobStoreContext])
    // var context = ContextBuilder.newBuilder("s3").endpoint(config.endPoint.get).overrides(overrides).credentials(config.storageKey, config.storageSecret).buildView(classOf[BlobStoreContext])

    var context = ContextBuilder.newBuilder("s3").endpoint(config.endPoint.get).modules(modules).credentials(config.storageKey, config.storageSecret).buildView(classOf[BlobStoreContext])
    // var context = ContextBuilder.newBuilder("s3").endpoint(config.endPoint.get).credentials(config.storageKey, config.storageSecret).buildView(classOf[S3BlobStoreContext])

    var blobStore = context.getBlobStore
    println(blobStore.getClass)

    override def getPaths(container: String, objects: List[Blob]): List[String] = {
        objects.map{f => "s3n://" + container + "/" + f.key}
    } 


}