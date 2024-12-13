pipeline {
    agent any
    tools {
        nodejs 'NodeJS_20'
        jdk 'JDK22'
        maven 'Maven'
    }
    environment {
        AWS_ACCESS_KEY_ID = 'your_access_key_id'
        AWS_SECRET_ACCESS_KEY = 'your_secret_access_key'
        AWS_S3_BUCKET_NAME = 'alumniportals3'
        AWS_S3_REGION = 'us-east-2'
        SLACK_WEBHOOK_URL = 'your_slack_webhook_url'
        SLACK_CHANNEL = '#alumniPortalDevelopment'
        SPRING_BOOT_APP_JAR = 'target/unmsm-0.0.1-SNAPSHOT.jar'
        SPRING_BOOT_APP_URL = 'http://localhost:8083'
        FRONTEND_URL = 'http://localhost:5173'
        SPRING_BOOT_BACKEND_SW = 'http://localhost:8083/swagger-ui/index.html'
        ZAP_PATH = '/opt/zap/zap.sh'
        PIPELINE_RESOURCES_FOLDER = '/var/jenkins_home/pipeline-resources'
        DOCKERHUB_CREDENTIALS = credentials('luisbdev-dockerhub')
    }
    stages {
        stage("Frontend - Git Checkout") {
            steps {
                git branch: 'testUnity', url: 'https://github.com/JeffersonCallupe/Alumni-Portal-Frontend.git'
            }
        }
        stage("Frontend - Install Dependencies") {
            steps {
                script {
                    echo "Installing frontend dependencies with npm"
                    sh "npm install"
                    echo "Installing testing dependencies"
                    sh "npm install -D @testing-library/react @testing-library/jest-dom @testing-library/user-event vitest"
                }
            }
        }
        stage("Frontend - Build") {
            steps {
                script {
                    echo "Building the frontend"
                    sh "npm run build"
                }
            }
        }
        stage("Frontend - Unit Tests") {
            steps {
                script {
                    echo "Running unit tests"
                    sh "npm test"
                }
            }
        }
         stage("Frontend - SonarQube Analysis") {
            steps {
                script {
                    def scannerHome = tool 'sonar-scanner'
                    withEnv(["PATH+SONAR_SCANNER=${scannerHome}/bin"]) {
                        sh """
                        sonar-scanner \
                        -Dsonar.projectKey=alumni-portal-frontend \
                        -Dsonar.sources=src \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=sqa_d1214bc66068f14ceb60fc07c0030efe7ce3f99b \
                        -Dsonar.exclusions=**/test/** \
                        -Dsonar.coverage.exclusions=**/test/**,**/*.spec.js,**/*.test.js
                        """
                    }
                }
            }
        }
        stage("Frontend - Start Server") {
            steps {
                script {
                    echo "Starting the frontend server"
                    sh "nohup npm run dev &"
                }
            }
        }
        stage("Frontend - Health Check") {
            steps {
                script {
                    echo "Checking frontend health"
                    sleep(time: 20, unit: "SECONDS")
                    def healthCheckUrl = "${env.FRONTEND_URL}"
                    def result = sh(script: "curl -s -o /dev/null -w '%{http_code}' ${healthCheckUrl}", returnStdout: true).trim()

                    if (result == "200") {
                        echo "Frontend health check passed"
                    } else {
                        error "Frontend health check failed with status ${result}"
                    }
                }
            }
        }
        stage("Frontend - Functional Tests") {
            steps {
                script {
                    echo "Running functional tests for the frontend"
                    
                    // Ejecutamos directamente el script python3 sin cambiar de directorio
                    sh "python3 ${PIPELINE_RESOURCES_FOLDER}/pruebas-funcionales-front/main_test.py"
                }
            }
        }
        stage("Frontend - Docker Build Image") {
            steps {
                sh "docker build -t luisbdev/alumni-frontend-image:latest . --no-cache"
            }
        }
        stage("Frontend - Docker Login") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'luisbdev-dockerhub', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                    sh '''
                        mkdir -p ~/.docker
                        echo '{"auths":{"https://index.docker.io/v1/":{"auth":"'"$(echo -n $DOCKERHUB_USERNAME:$DOCKERHUB_PASSWORD | base64)"'"}}}' > ~/.docker/config.json
                    '''
                }
            }
        }
        stage("Frontend - Docker Push Image") {
            steps {
                sh "docker push luisbdev/alumni-frontend-image:latest"
            }
        }
        stage("Docker Logout") {
            steps {
                sh "docker logout"
            }
        }
        stage("Backend - Git Checkout") {
            steps {
                git branch: 'master', url: 'https://github.com/LuisBDev/Alumni-Portal-Backend.git'
            }
        }
        stage("Backend - Build with Maven") {
            steps {
                sh "mvn clean compile -Pstg"
            }
        }
        stage("Backend - Run Tests") {
            steps {
                sh "mvn clean test"
            }
        }
        stage("Backend - SonarQube Analysis") {
            steps {
                script {
                    def scannerHome = tool 'sonar-scanner'
                    withEnv(["PATH+SONAR_SCANNER=${scannerHome}/bin"]) {
                        sh """
                        sonar-scanner \
                        -Dsonar.projectKey=test-alumni-cicd \
                        -Dsonar.sources=src \
                        -Dsonar.java.binaries=target/classes \
                        -Dsonar.host.url=http://sonarqube:9000 \
                        -Dsonar.login=sqa_d1214bc66068f14ceb60fc07c0030efe7ce3f99b \
                        -Dsonar.exclusions=**/test/** \
                        -Dsonar.coverage.exclusions=src/**/model/*,src/**/UnmsmApplication.java,src/**/util/**,src/**/exception/**,src/**/config/**,src/**/mapper/**
                        """
                    }
                }
            }
        }
        stage("Backend - Package App - Test Profile") {
            steps {
                sh "mvn clean package -DskipTests -Pstg"
            }
        }
        stage("Backend - Start Test Server") {
            steps {
                sh """
                java -jar ${SPRING_BOOT_APP_JAR} \
                --server.port=8083 \
                --spring.datasource.url=jdbc:mysql://alumni-mysql:3306/alumniportal \
                --AWS_ACCESS_KEY_ID=${env.AWS_ACCESS_KEY_ID} \
                --AWS_SECRET_ACCESS_KEY=${env.AWS_SECRET_ACCESS_KEY} \
                --AWS_S3_BUCKET_NAME=${env.AWS_S3_BUCKET_NAME} \
                --AWS_S3_REGION=${env.AWS_S3_REGION} &
                """
            }
        }
        stage("Backend - Health Check (Actuator)") {
            steps {
                script {
                    echo "Checking backend health"
                    sleep(time: 20, unit: "SECONDS") // Espera para que la app Spring Boot haya arrancado completamente
                    def healthCheckUrl = "${env.SPRING_BOOT_APP_URL}/actuator/health"
                    def result = sh(script: "curl -s -o /dev/null -w '%{http_code}' ${healthCheckUrl}", returnStdout: true).trim()

                    if (result == "200") {
                        echo "Backend health check passed"
                    } else {
                        error "Backend health check failed with status ${result}"
                    }
                }
            }
        }
        stage('Newman Setup') {
            steps {
                sh 'npm install -g newman'
                sh 'npm install -g newman-reporter-html'
                sh 'npm install -g newman-reporter-htmlextra'
            }
        }
        stage('Backend Pruebas Funcionales con Newman') {
            steps {
                sh 'newman run ${PIPELINE_RESOURCES_FOLDER}/alumninewman.postman_collection.json -r htmlextra --reporter-htmlextra-export ${PIPELINE_RESOURCES_FOLDER}/newman-report.html'
            }
        }
        stage("Cleaning Old JMeter Reports") {
            steps {
                sh "rm -rf ${PIPELINE_RESOURCES_FOLDER}/jmeter-report"
                sh "rm -rf ${PIPELINE_RESOURCES_FOLDER}/jmeter-results.jtl"
            }
        }
        stage("Performance Testing with JMeter") {
            steps {
                script {
                    echo "Running JMeter tests"
                    def jmeterHome = '/opt/jmeter'
                    def jmxFile = "${PIPELINE_RESOURCES_FOLDER}/TestingPlanAlumniPortal.jmx"
                    def jmeterLog = '/var/jenkins_home/jmeter.log'
                    def jmeterCommand = "${jmeterHome}/bin/jmeter -n -t ${jmxFile} -l ${PIPELINE_RESOURCES_FOLDER}/jmeter-results.jtl -j ${jmeterLog} -e -o ${PIPELINE_RESOURCES_FOLDER}/jmeter-report -Dserver_port=8090"

                    sh "${jmeterCommand}"
                }
            }
        }
        stage("Run ZAP Scan and Generate Report") {
            steps {
                script {
                    echo "Running ZAP Scan using OpenAPI definition"
                    sh """
                    ${env.ZAP_PATH} -cmd -port 8085 -quickurl ${env.SPRING_BOOT_BACKEND_SW} -quickout ${PIPELINE_RESOURCES_FOLDER}/zap-report.html
                    """
                }
            }
        }
        stage("Docker - Backend Build Image") {
            steps {
                sh "docker build -t luisbdev/alumni-app-image:latest --no-cache ."
            }
        }
        stage("Docker - Login") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'luisbdev-dockerhub', usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                    sh '''
                        mkdir -p ~/.docker
                        echo '{"auths":{"https://index.docker.io/v1/":{"auth":"'"$(echo -n $DOCKERHUB_USERNAME:$DOCKERHUB_PASSWORD | base64)"'"}}}' > ~/.docker/config.json
                    '''
                }
            }
        }
        stage("Docker - Push Image to DockerHub") {
            steps {
                sh "docker push luisbdev/alumni-app-image:latest"
            }
        }
        stage("Docker - Logout") {
            steps {
                sh "docker logout"
            }
        }
        stage("Send Slack Notification") {
            steps {
                script {
                    def message = "✅ *Build, Testing and Deployment Successful* \n*Job:* ${env.JOB_NAME} \n*Build Number:* ${env.BUILD_NUMBER} \n*URL:* ${env.BUILD_URL}"
                    sh """
                    curl -X POST -H 'Content-type: application/json' \
                    --data '{"channel": "${env.SLACK_CHANNEL}", "text": "${message}"}' \
                    ${env.SLACK_WEBHOOK_URL}
                    """
                }
            }
        }
    }
    post {
        failure {
            script {
                def message = "❌ *Pipeline Build Failed* \n*Job:* ${env.JOB_NAME} \n*Build Number:* ${env.BUILD_NUMBER} \n*URL:* ${env.BUILD_URL}"
                sh """
                curl -X POST -H 'Content-type: application/json' \
                --data '{"channel": "${env.SLACK_CHANNEL}", "text": "${message}"}' \
                ${env.SLACK_WEBHOOK_URL}
                """
            }
        }
        always {
            echo 'Pipeline complete.'
        }
    }
}
