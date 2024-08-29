pipeline {
    agent any

    stages {
        stage('Build') {
            agent {
                docker { image 'openjdk:11' } // Replace with the Docker image that suits your project's needs
            }
            steps {
                echo 'Building the application...'
                // Insert all your build commands directly here
                sh 'javac -d bin src/*.java' // Example for Java, replace with your actual commands
            }
        }
        stage('Unit and Integration Tests') {
            steps {
                echo 'Running unit and integration tests...'
                // Add commands to run your tests directly here
                sh 'java -cp bin org.junit.runner.JUnitCore TestClass' // Example, replace with your actual commands
            }
        }
        stage('Code Analysis') {
            agent {
                docker { image 'sonarsource/sonar-scanner-cli:latest' }
            }
            steps {
                echo 'Running code analysis...'
                // Run SonarQube analysis commands here
                sh 'sonar-scanner -Dsonar.projectKey=my_project -Dsonar.sources=. -Dsonar.host.url=http://localhost:9000 -Dsonar.login=your_sonarqube_token'
            }
        }
        stage('Security Scan') {
            agent {
                docker { image 'owasp/dependency-check:latest' }
            }
            steps {
                echo 'Performing security scan...'
                // Run security scan commands here
                sh 'dependency-check.sh --project "My Project" --scan /src'
            }
        }
        stage('Deploy to Staging') {
            agent {
                docker { image 'amazon/aws-cli:latest' }
            }
            steps {
                echo 'Deploying to Staging...'
                // Run deployment commands here
                sh 'aws deploy --application-name myApp --deployment-group myStagingGroup'
            }
        }
        stage('Integration Tests on Staging') {
            steps {
                echo 'Running integration tests on staging...'
                // Run your integration tests directly here
                sh 'java -cp bin org.junit.runner.JUnitCore StagingTestClass' // Example, replace with your actual commands
            }
        }
        stage('Deploy to Production') {
            agent {
                docker { image 'amazon/aws-cli:latest' }
            }
            steps {
                echo 'Deploying to Production...'
                // Run production deployment commands here
                sh 'aws deploy --application-name myApp --deployment-group myProductionGroup'
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
