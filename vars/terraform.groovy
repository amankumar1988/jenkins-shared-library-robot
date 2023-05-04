def call() {
        if(!env.TFDIR) {
            env.TFDIR = "./"
        }
    properties([
        parameters([
            choice(choices: 'dev\nprod', description: "Choice the Envt.", name: "ENV"),
            choice(choices: 'apply\ndestroy', description: "Choice the Action.", name: "ACTION"),
            string(choices: 'APP_VERSION', description: "Enter the Backend Version to be deployed, IGNRORE THIS IF THIS IS BACKEND COMPONENT", name: "APP_VERSION"),
        ]),
    ])
    node {
    ansiColor('xterm') {
        sh "rm -rf *"
        git branch: 'main', url: "https://github.com/amankumar1988/${REPONAME}.git"

            stage('Terraform Init'){
                sh '''
                    cd ${TFDIR}
                    terrafile -f env-dev/Terrafile
                    terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars
                '''
            }
            stage('Terraform Plan'){
                sh '''
                    cd ${TFDIR}
                    terraform plan -var-file=env-${ENV}/${ENV}.tfvars
                '''
            }
            stage('Terraform Apply'){
                sh '''
                    cd ${TFDIR}
                    terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars
                '''
            }
        }
    }
}

    // pipeline {
    //     agent any
    //     parameters { 
    //         choice(name: 'ENV', choices: ['dev', 'prod'], description: 'Choose the Envt.') 
    //         choice(name: 'ACTION', choices: ['apply', 'destroy'], description: 'Chose Apply or Destroy.') 
    //         }
    //     options {
    //         ansiColor('xterm')
    //     }
    //     stages {
    //         stage('Terraform Init'){
    //             steps{
    //                 sh "terrafile -f env-dev/Terrafile"
    //                 sh "terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars"
    //             }
    //         }
    //         stage('Terraform Plan'){
    //             steps{
    //                 sh "terraform plan -var-file=env-${ENV}/${ENV}.tfvars"
    //             }
    //         }
    //         stage('Terraform Apply'){
    //             steps{
    //                 sh "terraform ${ACTION} -auto-approve -var-file=env-${ENV}/${ENV}.tfvars"
    //             }
    //         }
    //     }
    // }

