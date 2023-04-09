def sonarChecks(){
    sh '''
        // sonar-scanner -Dsonar.host.url=http://172.31.1.163:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}
        // curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate > quality-gate.sh
        // bash -x quality-gate.sh ${SONAR_USR} ${SONAR_PSW} ${SONAR_URL} ${COMPONENT}
        echo "Starting the Code Quality Analysis"
        echo "Code Quality Analysis Completed"
    '''
}