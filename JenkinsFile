pipeline {
    agent any
    stages {
        stage('Clone sources') {
            steps {
                git url: 'https://github.com/ysmimaa/driver-service.git'
            }
        }
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh "gradle sonarqube"
                }
            }
        }
        stage("Quality gate") {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
    }
}
