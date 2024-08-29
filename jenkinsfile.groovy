pipeline {
    agent any

    stages {
        stage('Build') {
            agent {
                docker { image 'maven:3.8.5-openjdk-11' }
            }
            steps {
                echo 'Building the application...'
                // Use Maven inside a Docker container
                sh 'mvn clean install'
            }
        }
        stage('Unit and Integration Tests') {
            agent {
                docker { image 'maven:3.8.5-openjdk-11' }
            }
            steps {
                echo 'Running unit and integration tests...'
                // Use Maven to run tests inside a Docker container
                sh 'mvn test'
            }
        }
        stage('Code Analysis') {
            agent {
                docker { image 'sonarsource/sonar-scanner-cli:latest' }
            }
            steps {
                echo 'Running code analysis...'
                // Use SonarQube scanner inside a Docker container
                sh 'sonar-scanner -Dsonar.projectKey=my_project -Dsonar.sources=. -Dsonar.host.url=http://localhost:9000 -Dsonar.login=your_sonarqube_token'
            }
        }
        stage('Security Scan') {
            agent {
                docker { image 'owasp/dependency-check:latest' }
            }
            steps {
                echo 'Performing security scan...'
                // Use OWASP Dependency-Check inside a Docker container
                sh 'dependency-check.sh --project "My Project" --scan /src'
            }
        }
        stage('Deploy to Staging') {
            agent {
                docker { image 'amazon/aws-cli:latest' }
            }
            steps {
                echo 'Deploying to Staging...'
                // Use AWS CLI inside a Docker container
                sh 'aws deploy --application-name myApp --deployment-group myStagingGroup'
            }
        }
        stage('Integration Tests on Staging') {
            agent {
                docker { image 'maven:3.8.5-openjdk-11' }
            }
            steps {
                echo 'Running integration tests on staging...'
                // Use Maven to run tests inside a Docker container
                sh 'mvn verify'
            }
        }
        stage('Deploy to Production') {
            agent {
                docker { image 'amazon/aws-cli:latest' }
            }
            steps {
                echo 'Deploying to Production...'
                // Use AWS CLI inside a Docker container
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
