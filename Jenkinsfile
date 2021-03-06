#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "chmod +x mvnw"
        sh "./mvnw clean"
    }

    stage('backend tests') {
        try {
            sh "./mvnw test"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }

    stage('packaging') {
        sh "./mvnw verify -Pprod,swagger,zipkin -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }

    stage('deploy') {
        sh "./mvnw deploy -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }

    stage('quality analysis') {
        withSonarQubeEnv('Sonar') {
            sh "./mvnw sonar:sonar -Dsonar.host.url=http://sonarqube.xedflix.com -Dsonar.login=c923f5d637e3171d273186b9b66df095854c6bf1"
        }
    }
}
