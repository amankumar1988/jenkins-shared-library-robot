def call() {
    node {
    properties([
        parameters([
            choice(choices: ['dev\nprod'], description: "Choice the Envt.", name: "ENV"),
            choice(choices: ['apply\ndestroy'], description: "Choice the Action.", name: "ACTION"),
        ]),
    ])
    ansiColor('xterm') {
            stage('Terraform Init'){
                sh '''
                    terrafile -f env-dev/Terrafile"
                    terraform init -backend-config=env-${ENV}/${ENV}-backend.tfvars
                '''
            }
            stage('Terraform Plan'){
                sh '''
                    terraform plan -var-file=env-${ENV}/${ENV}.tfvars
                '''
            }
            stage('Terraform Apply'){
                sh '''
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

