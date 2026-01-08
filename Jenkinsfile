pipeline {
    agent any
    
    tools {
        maven 'Maven-3.8'
        jdk 'JDK-11'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '📦 Checking out code from GitHub...'
                checkout scm
            }
        }
        
        stage('Build JAR') {
            steps {
                echo '🔨 Building JAR with Maven...'
                sh 'mvn clean package'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                echo '🐳 Building Docker image...'
                script {
                    def imageTag = "java-app:${BUILD_NUMBER}"
                    sh "docker build -t ${imageTag} ."
                    env.IMAGE_TAG = imageTag
                }
            }
        }
        
        stage('Push to K3d') {
            steps {
                echo '📦 Importing image to K3d...'
                sh "k3d image import ${IMAGE_TAG} -c java-cluster"
            }
        }
        
        stage('Deploy to Kubernetes') {
            steps {
                echo '🚀 Deploying to Kubernetes...'
                sh """
                    kubectl set image deployment/java-app java-app=${IMAGE_TAG} || \
                    kubectl create deployment java-app --image=${IMAGE_TAG}
                    
                    kubectl expose deployment java-app --port=80 --target-port=8080 --type=ClusterIP || true
                """
            }
        }
        
        stage('Verify Deployment') {
            steps {
                echo '✅ Verifying deployment...'
                sh 'kubectl rollout status deployment/java-app'
                sh 'kubectl get pods -l app=java-app'
            }
        }
    }
    
    post {
        success {
            echo '🎉 CI/CD Pipeline completed successfully!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}
