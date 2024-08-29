pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building the project...'
                echo 'Running: mvn clean package'
            }
        }
        
        stage('Unit and Integration Tests') {
            steps {
                echo 'Running tests...'
                echo 'Running: mvn test'
            }
            post{
                always{
                   emailext ( to: "ehsentahir@gmail.com",
                   subject: "Test Phase - Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                   body: '''<html>
                               <body>
                                   <p>Build Status: ${BUILD_STATUS}</p>
                                   <p>Build Number: ${BUILD_NUMBER}</p>
                                   <p>Check the <a href="${BUILD_URL}">console output</a>.</p>
                                </body>
                            </html>''',
                   attachLog: true,
                   mimeType: 'text/html'
                )}
            }
        }
        
        stage('Code Analysis') {
            steps {
                echo 'Analyzing code...'
                echo 'Running: mvn sonar:sonar'
            }
        }
        
        stage('Security Scan') {
            steps {
                echo 'Performing security scan...'
                echo 'Running: mvn org.owasp:dependency-check-maven:check'
            }
            post{
                always{
                   emailext ( to: "ehsentahir@gmail.com",
                   subject: "Security Scan - Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                   body: '''<html>
                               <body>
                                   <p>Build Status: ${BUILD_STATUS}</p>
                                   <p>Build Number: ${BUILD_NUMBER}</p>
                                   <p>Check the <a href="${BUILD_URL}">console output</a>.</p>
                                </body>
                            </html>''',
                   attachLog: true,
                   mimeType: 'text/html'
                )}
            }
        }
        
        stage('Deploy to Staging') {
            steps {
                echo 'Deploying to staging...'
                echo 'Simulating deployment to AWS EC2 instance...'
            }
        }
        
        stage('Integration Tests on Staging') {
            steps {
                echo 'Running integration tests on staging...'
                echo 'Simulating integration testing...'
            }
        }
        
        stage('Deploy to Production') {
            steps {
                echo 'Deploying to production...'
                echo 'Simulating deployment to AWS EC2 instance...'
            }
        }
    }
}
