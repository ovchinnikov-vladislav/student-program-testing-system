pipeline {
    agent any
    stages {
        stage('clean') {
            steps {
                sh './gradlew clean'
            }
        }

	stage('build and test') {
	    steps {
		sh './gradlew build'
	    }
        }

	stage('bootJar') {
	    steps {
		sh './gradlew bootJar'
	    }
	}

	stage('start docker') {
	    steps {
		sh 'sudo chmod +x start_docker.sh'
	    	sh 'sudo ./start_docker.sh'
	    }	
	}

	stage('dockerizing') {
	    steps {
		sh 'sudo chmod +x create_service_images_docker.sh'
	    	sh 'sudo ./create_service_images_docker.sh'
    	    }
        }

	stage('deploy.sh') {
	    steps {
		sh 'sudo chmod +x deploy.sh'
	    	sh 'sudo ./deploy.sh'
	    }	
	}
    }
}
