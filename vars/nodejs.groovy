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
        SONAR = credentials('SONAR')
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

            stage ('Performing npm install') {
                steps{
                    sh "echo Hi this is test"
                }
            }
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

            stage('Prepare the artifacts') {
                when { expression {env.TAG_NAME != null } }
                steps{
                    sh "echo Prepare the artifacts"
                }
            }
            stage('Publish the artifacts') {
                steps{
                    sh "echo Publishing the artifacts"
                }
            }

        }
    }
}