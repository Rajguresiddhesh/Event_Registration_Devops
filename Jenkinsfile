// ==============================================================================
// Jenkinsfile: Declarative Pipeline for College Event Registration Platform
// Student: 23102A0003 - SIDDHESH RAJGURE
// Project: Jenkins-Based College Event Registration Platform
// Cross-Platform Compatible: Works on Linux/Docker and Windows Jenkins Agents
// ==============================================================================

pipeline {
    agent any

    tools {
        maven 'Maven-3.9.6'
    }

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['staging', 'production', 'testing'],
            description: 'Target Deployment Environment'
        )
        string(
            name: 'APP_PORT',
            defaultValue: '8080',
            description: 'Target Application HTTP Port'
        )
        booleanParam(
            name: 'RUN_SELENIUM',
            defaultValue: true,
            description: 'Execute Selenium Automated UI Quality Gate'
        )
    }

    options {
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }

    environment {
        APP_NAME = 'college-event-registration-platform'
        STUDENT_NAME = 'SIDDHESH RAJGURE'
        STUDENT_ROLL = '23102A0003'
        TARGET_DIR = 'target'
        JAR_NAME = 'college-event-registration-platform.jar'
    }

    stages {
        stage('1. Checkout SCM & Environment Audit') {
            steps {
                echo "======================================================================"
                echo "Starting CI Build for ${APP_NAME}"
                echo "Candidate: ${STUDENT_NAME} (Roll: ${STUDENT_ROLL})"
                echo "Target Environment: ${params.ENVIRONMENT} on Port: ${params.APP_PORT}"
                echo "Git Commit: ${env.GIT_COMMIT ?: 'Local-Workspace'}"
                echo "======================================================================"
                
                script {
                    if (isUnix()) {
                        sh 'java -version || true'
                        sh 'mvn -version || true'
                    } else {
                        bat 'java -version || ver'
                        bat 'mvn -version || ver'
                    }
                }
            }
        }

        stage('2. Build & Compile Application') {
            steps {
                echo "Compiling application source files..."
                script {
                    if (isUnix()) {
                        sh 'mvn clean compile -DskipTests'
                    } else {
                        bat 'mvn clean compile -DskipTests'
                    }
                }
            }
        }

        stage('3. Unit & Integration Testing') {
            steps {
                echo "Executing JUnit 5 Unit Tests and Service Layer Validations..."
                script {
                    if (isUnix()) {
                        sh 'mvn test -Dtest=EventRegistrationServiceTest,RegistrationControllerTest,CollegeEventRegistrationApplicationTests'
                    } else {
                        bat 'mvn test -Dtest=EventRegistrationServiceTest,RegistrationControllerTest,CollegeEventRegistrationApplicationTests'
                    }
                }
            }
        }

        stage('4. Selenium Automated Quality Gate') {
            when {
                expression { return params.RUN_SELENIUM }
            }
            steps {
                echo "Executing Selenium WebDriver End-to-End User Journey Tests..."
                script {
                    if (isUnix()) {
                        sh 'mvn test -Dtest=*JourneyTest -Dselenium.required=true'
                    } else {
                        bat 'mvn test -Dtest=*JourneyTest -Dselenium.required=true'
                    }
                }
            }
        }

        stage('5. Package Application Artifact') {
            steps {
                echo "Packaging deployable executable JAR..."
                script {
                    if (isUnix()) {
                        sh 'mvn package -DskipTests'
                    } else {
                        bat 'mvn package -DskipTests'
                    }
                }
            }
        }

        stage('6. Archive Build Artifacts') {
            steps {
                echo "Archiving generated JAR and test artifacts..."
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                archiveArtifacts artifacts: 'target/selenium-screenshots/*.png', allowEmptyArchive: true
            }
        }

        stage('7. Automated Server Deployment') {
            steps {
                echo "Deploying application artifact to ${params.ENVIRONMENT} server..."
                script {
                    echo "Deployment is not configured in this CI demo; skipping deployment."
                }
            }
        }

        stage('8. Post-Deploy Health Check Probe') {
            steps {
                echo "Validating live application health via Actuator endpoint..."
                script {
                    echo "Health probe is skipped because no deployment target is configured."
                }
            }
        }
    }

    post {
        always {
            echo "Publishing Surefire / JUnit test execution reports..."
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
            cleanWs deleteDirs: false, notFailBuild: true
        }
        success {
            echo "SUCCESS: CI/CD Pipeline completed successfully for ${STUDENT_ROLL} - ${STUDENT_NAME}!"
        }
        failure {
            echo "FAILURE: Quality gate broken or build failure! Halting deployment pipeline immediately."
        }
    }
}
