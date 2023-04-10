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

def testCases(){
    stage('Test Cases'){
        def stages = [:]

        stages["Unit Testing"] = {
            echo "Unit Testing Started"
            echo "Unit Testing Completed"
            // sh mvn test or npm test
        }
        stages["Integration Testing"] = {
            echo "Integration Testing Started"
            echo "Integration Testing Completed"
            // sh mvn verify or npm verify
        }
        stages["Functional Testing"] = {
            echo "Functional Testing Started"
            echo "Functional Testing Completed"
            // sh mvn verify or npm verify
        }

        parallel(stages)
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
// I only want to run check the release, check the artifact and push the artifact to nexus, only if the artifact doesn't exist
def artifacts(){

    stage('Check the Release'){
        env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl -L -s http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true')
        print UPLOAD_STATUS
    }
    if (env.UPLOAD_STATUS == ''){
        stage('Preparing the artifact'){
            if(env.APP_TYPE == "nodejs"){
            sh '''
            npm install
            echo Preparing the artifacts"
            zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js"
            '''
        }
        else if(env.APP_TYPE =="maven"){
         sh '''
             mvn clean package
             mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar 
            '''
        }
        else if(env.APP_TYPE =="python"){
            sh '''
                zip -r ${COMPOMENT}-${TAG_NAME}.zip *.py *.ini requirements.txt
            '''
            }
        else{
            sh '''
                echo "Frontend Component Is Executing"
                cd static/
                zip -r ../${COMPONENT}-${TAG_NAME}.zip *
                '''
            }
        }
        stage('Upload the artifacts'){
            withCredentials([usernamePassword(credentialsId: 'NEXUS', passwordVariable: 'NEXUS_PSW', usernameVariable: 'NEXUS_USR')]) {
            sh "curl  -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
            }
        }
    }
}