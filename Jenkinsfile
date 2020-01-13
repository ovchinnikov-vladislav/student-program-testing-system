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
	    	sh './start_docker.sh'
	    }	
	}

	stage('dockerizing') {
	    steps {
	    	sh './create_service_images_docker.sh'
    	    }
        }

	stage('deploy.sh') {
	    steps {
	    	sh './deploy.sh'
	    }	
	}
    }
}
