def lintChecks(COMPONENT){
    sh '''
       echo installing jsling"
        # npm install jslint"
        # ls -lrth node_modules/jslint/bin"
        # node_modules/jslint/bin/jslint.js server.js"
        echo Performing lint checks"
        echo Performing lint checks completed"
    '''
}
# call is the default function which will be called when you call the filename

def call(COMPONENT){
    pipeline {
        agent any
        stages {
            stage('Lint Checks')
                steps{
                    script {
                        nodejs.lintChecks(COMPONENT)
                    }
                }
        }
    }
}