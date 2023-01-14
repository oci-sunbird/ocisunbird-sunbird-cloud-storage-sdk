node('build-slave') {
    try {
        String ANSI_GREEN = "\u001B[32m"
        String ANSI_NORMAL = "\u001B[0m"
        String ANSI_BOLD = "\u001B[1m"
        String ANSI_RED = "\u001B[31m"
        String ANSI_YELLOW = "\u001B[33m"

        ansiColor('xterm') {
          withEnv(["JAVA_HOME=${JAVA11_HOME}", "oci_endpoint_url=${params.oci_endpoint_url}", "oci_storage_container=${params.oci_storage_container}", "oci_storage_container_target=${params.oci_storage_container_target}", "oci_storage_key=${params.oci_storage_key}", "oci_storage_secret=${params.oci_storage_secret}","oci_region=${params.oci_region}"]) {
                stage('Checkout') {
                    if (!env.hub_org) {
                        println(ANSI_BOLD + ANSI_RED + "Uh Oh! Please set a Jenkins environment variable named hub_org with value as registry/sunbidrded" + ANSI_NORMAL)
                        error 'Please resolve the errors and rerun..'
                    } else
                        println(ANSI_BOLD + ANSI_GREEN + "Found environment variable named hub_org with value as: " + hub_org + ANSI_NORMAL)
                }
                cleanWs()
                checkout scm
                commit_hash = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()
                build_tag = sh(script: "echo " + params.github_release_tag.split('/')[-1] + "_" + commit_hash + "_" + env.BUILD_NUMBER, returnStdout: true).trim()
                echo "build_tag: " + build_tag

                stage('Build') {
                    env.NODE_ENV = "build"
                    print "Environment will be : ${env.NODE_ENV}"
                    sh '/opt/apache-maven-3.6.3/bin/mvn3.6 -v'
                    sh 'echo OCI Storage Endpoint $oci_storage_endpoint'
                    sh '/opt/apache-maven-3.6.3/bin/mvn3.6 -Dtest=TestOCIS3StorageService clean package'
                  
                }

//                stage('ArchiveArtifacts') {
//                    archiveArtifacts "metadata.json"
//                    currentBuild.description = "${build_tag}"
//                }
            }
        }
    }
    catch (err) {
        currentBuild.result = "FAILURE"
        throw err
    }
}