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
                        common.sonarChecks()
                    }
                }  
             }

            stage ('Performing npm install') {
                steps{
                    sh "echo Hi this is test"
                }
            }


        }
    }
}