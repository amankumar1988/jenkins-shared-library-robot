// def lintChecks(COMPONENT){
//     sh '''
//         echo Lint Checks for ${COMPONENT}       
//         # pylint *.py
//         echo Performing lint checks for ${COMPONENT}
//         echo Performing lint checks completed ${COMPONENT}
//     '''
// }

def call() {
    node {
        git branch: 'main', url: "https://github.com/b53-clouddevops/${COMPONENT}.git"
        env.APP_TYPE = "python" 
        common.lintChecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarChecks()
        common.testCases()
        common.artifacts()
    }
}

// call is the default function which will be called when you call the filename


// def call() {
//     pipeline {
//         agent any
//         environment{
//         SONAR_URL = "172.31.1.163"
//         SONAR = credentials('SONAR')
//         }
//         stages {
//             stage('Lint Checks') {
//                 steps{
//                     script {
//                         lintChecks()
//                     }
//                 }  
//              }
//             stage('Sonar Checks') {
//                 steps{
//                     script {
//                         env.ARGS ="-Dsonar.sources=."
//                         common.sonarChecks()
//                     }
//                 }  
//              }

//         stage('Test Cases') {
//           parallel {
//             stage('Unit Test') {
//                 steps{
//                     // sh "mvn test"
//                     sh "echo Performing Unit Testing"
//                 }
//             }   
//             stage('Integration Test') {
//                 steps{
//                      // sh "mvn verify"
//                     sh "echo Performing Integration Testing"
//                 }
            
//             }
//                 stage('Functional Test') {
//                     steps{
//                          // sh "mvn verify"
//                         sh "echo Performing Functional Testing"
//                         }
//                     }
//                 }
//             }  

//         }
//     }
// }