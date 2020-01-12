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
		sh '.gradlew build'
	    }
        }

	stage('bootJar') {
	    steps {
		sh './gradlew bootJar'
	    }
	}
    }
}
