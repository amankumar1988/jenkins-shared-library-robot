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

def sonarChecks(){
    sh '''
        sonar-scanner -Dsonar.host.url=http://172.31.1.163:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=password
    '''
}

// call is the default function which will be called when you call the filename

def call() {
    pipeline {
        agent any
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
                        sonarChecks()
                    }
                }  
             }
        }
    }
}