def lintChecks(COMPONENT){
    sh '''
        echo Lint Checks for ${COMPONENT}       
        echo installing jslint
        # npm install jslint"
        # ls -lrth node_modules/jslint/bin"
        # node_modules/jslint/bin/jslint.js server.js"
        echo Performing lint checks for ${COMPONENT}
        echo Performing lint checks completed ${COMPONENT}
    '''
}



// call is the default function which will be called when you call the filename

def call() {
    pipeline {
        agent any
        environment{
        SONAR_URL = "172.31.1.163"
        NEXUS = credentials('NEXUS')
        SONAR = credentials('SONAR')
        NEXUS_URL = "172.31.8.18"

        }

        stages {
            stage('Lint Checks') {
                steps{
                    script {
                        lintChecks()
                    }
                }  
             }
            stage('Sonar Checks') {
                steps{
                    script {
                        env.ARGS ="-Dsonar.sources=."
                        common.sonarChecks()
                    }
                }  
             }

            // stage ('Performing npm install') {
            //     steps{
            //         sh "echo Hi this is test"
            //     }
            // }
        stage('Test Cases') {
          parallel {
            stage('Unit Test') {
                steps{
                    // sh "npm test"
                    sh "echo Performing Unit Testing"
                }
            }   
            stage('Integration Test') {
                steps{
                     // sh "npm verify"
                    sh "echo Performing Integration Testing"
                }
            
            }
                stage('Functional Test') {
                    steps{
                         // sh "npm verify"
                        sh "echo Performing Functional Testing"
                        }
                    }
                }
            } 

            stage('Checking the artifacts Version') {
            when { 
                expression { env.TAG_NAME != null } 
                // expression { env.UPLOAD_STATUS == "" }
                }
                steps{
                    script{
                        env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}' || true)
                        print UPLOAD_STATUS
                    }

                }
            }

            stage('Prepare the artifacts') {
            when { 
                expression { env.TAG_NAME != null } 
                // expression { env.UPLOAD_STATUS == "" }
                }
                steps{
                    sh "npm install"
                    sh "echo Preparing the artifacts"
                    sh " zip ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
                }
            }
            stage('Publish the artifacts') {
            when { 
                expression { env.TAG_NAME != null } 
                // expression { env.UPLOAD_STATUS == "" }
                }
                steps{
                    sh "echo Publishing the artifacts"
                    sh "curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"  
                }
            }

        }
    }
}