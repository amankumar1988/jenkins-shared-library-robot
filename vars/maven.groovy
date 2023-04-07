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