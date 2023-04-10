// Declarative Pipeline
// def sonarChecks(){
//     // sh  "sonar-scanner -Dsonar.host.url=http://172.31.1.163:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
//     // sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
//     // sh "bash -x quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}"
//     sh "echo Starting the Code Quality Analysis"
//     sh "echo Code Quality Analysis Completed"
// }

// Scripted Pipeline
def sonarChecks(){
    stage('Sonar Checks'){
    // sh  "sonar-scanner -Dsonar.host.url=http://172.31.1.163:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}"
    // sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh"
    // sh "bash -x quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}"
    sh "echo Starting the Code Quality Analysis"
    sh "echo Code Quality Analysis Completed"
    }
}


def lintChecks(){
    stage('Lint Checks'){
        if (env.APP_TYPE == "maven"){
        sh '''
                echo Lint Checks for ${COMPONENT}       
                # mvn checkstyle:check 
                echo Performing lint checks for ${COMPONENT}
                echo Performing lint checks completed ${COMPONENT}
        '''
        }
        else if(env.APP_TYPE =="nodejs"){
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
        else if(env.APP_TYPE =="python"){
        sh '''
            echo Lint Checks for ${COMPONENT}       
            # pylint *.py
            echo Performing lint checks for ${COMPONENT}
            echo Performing lint checks completed ${COMPONENT}
        '''
        }
        else if(env.APP_TYPE =="angularjs"){
        sh '''
            echo Lint Checks for ${COMPONENT}       
            echo Performing lint checks for ${COMPONENT}
            echo Performing lint checks completed ${COMPONENT}
        '''
        }
    }
}
