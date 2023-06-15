def call() {
    node {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/amankumar1988/${COMPONENT}.git"
        common.lintChecks()

    if ( env.TAG_NAME != null ) {
        stage('Preparing the artifact') {
            if(env.APP == "nodejs") {
                sh ''' 
                    npm install
                    ls -ltr
                    # zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js
                '''      
            }
            else if(env.APP == "maven") {  
                sh '''
                    mvn clean package
                    cp target/${COMPONENT}-1.0.jar ${COMPONENT}.jar 
                    ls -ltr 
                    # zip -r ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar
                '''
            }
            else if(env.APP == "python") {  
                sh '''
                    zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirements.txt
                '''
            }
            else {  
                sh '''
                    echo "Frontend Component Is Executing"
                    zip -r ${COMPONENT}-${TAG_NAME}.zip * 
                    zip -d ${COMPONENT}-${TAG_NAME}.zip Jenkinsfile

                    '''
                }
                    sh "env"
                    sh "docker build -t 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest ."
                    sh "docker tag 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
                    sh "aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 834725375088.dkr.ecr.us-east-1.amazonaws.com"
                    sh "docker push 834725375088.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}"
                }
            }
        }
    }

        // env.APP_TYPE = "maven" 
        // common.lintChecks()
        // env.ARGS="-Dsonar.java.binaries=target/"
        // common.sonarChecks()
        // common.testCases()
        // common.artifacts()