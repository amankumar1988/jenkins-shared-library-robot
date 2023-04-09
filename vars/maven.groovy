def lintChecks(COMPONENT){
    sh '''
        echo Lint Checks for ${COMPONENT}       
        # mvn checkstyle:check 
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
                        sh "mvn clean compile"
                        env.ARGS="-Dsonar.java.brinaries=target/"
                        common.sonarChecks()
                    }
                }  
             } 

        stage('Test Cases') {
          parallel {
            stage('Unit Test') {
                steps{
                    // sh "mvn test"
                    sh "echo Performing Unit Testing"
                }
            }   
            stage('Integration Test') {
                steps{
                     // sh "mvn verify"
                    sh "echo Performing Integration Testing"
                }
            
            }
                stage('Functional Test') {
                    steps{
                         // sh "mvn verify"
                        sh "echo Performing Functional Testing"
                        }
                    }
                }
            }  
             
        }
    }
}