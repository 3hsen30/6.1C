pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building the application...'
                // Example build command, replace with your actual build command
                // sh 'mvn clean install'
            }
        }
        stage('Unit and Integration Tests') {
            steps {
                echo 'Running unit and integration tests...'
                // Example test command, replace with your actual test command
                // sh 'mvn test'
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'Running code analysis...'
                // Example code analysis tool, replace with your actual tool command
                // sh 'sonar-scanner'
            }
        }
        stage('Security Scan') {
            steps {
                echo 'Performing security scan...'
                // Example security scan command, replace with your actual tool command
                // sh 'dependency-check --project example --scan .'
            }
        }
        stage('Deploy to Staging') {
            steps {
                echo 'Deploying to Staging...'
                // Example deployment command, replace with your actual deployment script/command
                // sh 'deploy script or command for staging'
            }
        }
        stage('Integration Tests on Staging') {
            steps {
                echo 'Running integration tests on staging...'
                // Example command for integration tests on staging
                // sh 'integration tests command'
            }
        }
        stage('Deploy to Production') {
            steps {
                echo 'Deploying to Production...'
                // Example deployment command, replace with your actual deployment script/command
                // sh 'deploy script or command for production'
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
        }
        success {
            emailext (
                subject: "Jenkins Pipeline Success",
                body: "The pipeline completed successfully. Please review the details in Jenkins.",
                to: 'ehsentahir@gmail.com'
            )
        }
        failure {
            emailext (
                subject: "Jenkins Pipeline Failure",
                body: "The pipeline failed. Please check the Jenkins logs for more details.",
                to: 'ehsentahir@gmail.com'
            )
        }
    }
}
