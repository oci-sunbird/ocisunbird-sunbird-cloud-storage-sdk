package org.sunbird.cloud.storage.conf

import com.typesafe.config.ConfigFactory
import com.typesafe.config.Config

object AppConf {

    lazy val defaultConf = ConfigFactory.load();
    lazy val envConf = ConfigFactory.systemEnvironment();
    lazy val conf = envConf.withFallback(defaultConf);

    def getConfig(key: String): String = {
        if (conf.hasPath(key))
            conf.getString(key);
        else "";
    }

    def getConfigList(key: String): Option[String] = {
        if (conf.hasPath(key))
            Option(conf.getString(key));
        else Option("");
    }

    def getConfig(): Config = {
        return conf;
    }

    def getAwsKey(): String = {
        getConfig("aws_storage_key");
    }

    def getAwsSecret(): String = {
        getConfig("aws_storage_secret");
    }

    def getOciKey(): String = {
        getConfig("oci_storage_key");
    }

    def getOciSecret(): String = {
        getConfig("oci_storage_secret");
    }

    def getOciEnpoint(): Option[String] = {
        getConfigList("oci_storage_endpoint");
    }

    def getStorageType(): String = {
        getConfig("cloud_storage_type");
    }

    def getOciRegion(): String = {
        getConfig("oci_region");
    }
    def getStorageKey(`type`: String): String = {
        if (`type`.equals("aws")) getConfig("aws_storage_key");
        else if (`type`.equals("azure")) getConfig("azure_storage_key");
        else if (`type`.equals("cephs3")) getConfig("cephs3_storage_key");
        else if (`type`.equals("gcloud")) getConfig("gcloud_client_key");
        else if (`type`.equals("oci")) getConfig("oci_storage_key");
        else "";
    }

    def getStorageSecret(`type`: String): String = {
        if (`type`.equals("aws")) getConfig("aws_storage_secret");
        else if (`type`.equals("azure")) getConfig("azure_storage_secret");
        else if (`type`.equals("cephs3")) getConfig("cephs3_storage_secret");
        else if (`type`.equals("gcloud")) getConfig("gcloud_private_secret");
        else if (`type`.equals("oci")) getConfig("oci_storage_secret");
        else "";
    }

    def getStorageEndpoint(`type`: String): Option[String] = {
        if (`type`.equals("oci")) getConfigList("oci_storage_endpoint");
        else Option("");
    }

    def getRegion(`type`: String): String = {
        if (`type`.equals("oci")) getConfig("oci_region");
        else "";
    }

}
