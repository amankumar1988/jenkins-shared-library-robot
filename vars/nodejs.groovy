def lintChecks(COMPONENT){
    sh '''
        echo Lint Checks for ${COMPONENT}       
        echo installing jsling"
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
        stages {
            stage('Lint Checks') {
                steps{
                    script {
                        lintChecks()
                    }
                }  
             }
        }
    }
}