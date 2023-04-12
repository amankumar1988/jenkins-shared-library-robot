// def lintChecks(COMPONENT){
//     sh '''
//         echo Lint Checks for ${COMPONENT}       
//         echo Performing lint checks for ${COMPONENT}
//         echo Performing lint checks completed ${COMPONENT}
//     '''
// }



def call() {
    node{
        env.APP_TYPE= "angularjs"
        common.lintChecks()
        env.ARGS ="-Dsonar.sources=."
        common.sonarChecks()
        common.testCases()
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
//                     // sh "npm test"
//                     sh "echo Performing Unit Testing"
//                 }
//             }   
//             stage('Integration Test') {
//                 steps{
//                      // sh "npm verify"
//                     sh "echo Performing Integration Testing"
//                 }
            
//             }
//                 stage('Functional Test') {
//                     steps{
//                          // sh "npm verify"
//                         sh "echo Performing Functional Testing"
//                         }
//                     }
//                 }
//             }  

//         }
//     }
// }