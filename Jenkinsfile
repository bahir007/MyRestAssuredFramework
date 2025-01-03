pipeline 
{
    agent any
    
    tools{
    	maven 'maven'
        }
    environment{
		
		BUILD_NUMBER = "${BUILD_NUMBER}"
	}

    stages 
    {
        stage('Build') 
        {
            steps
            {
                 git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                 sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post 
            {
                success
                {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
        
        
        
        stage("Deploy to QA"){
            steps{
                echo("deploy to qa done")
            }
        }
        
        stage('Run Docker Image with Regression Tests'){
			
			steps{
				script{
					def exitCode = sh(script: "docker run --name apitesting${BUILD_NUMBER} -e MAVEN_OPTS='-Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml' bahir007/apitestautomation3:latest", returnStatus: true)
						if (exitCode !=0){
							
							currentBuild.result = 'FAILURE' //Mark the build as failed if tests fail
						}
						// Even if tests fail, copy the report (if present)
						sh "docker start apittesting${BUILD_NUMBER}"
						// sh "sleep 60"
						sh "docker cp apitesting${BUILD_NUMBER}:/app/reports/APIExecutionReport.html ${WORKSPACE}/reports"
						sh "docker rm -f apitesting${BUILD_NUMBER}"
				
				}
			}
		}
                
      //  stage('Regression API Automation Test') {
      //      steps {
      //          catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
      //              git 'https://github.com/bahir007/MyRestAssuredFramework.git'
      //              sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_regression.xml"
                    
      //          }
      //      }
      //  }
                
     
        stage('Publish Allure Reports') {
           steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: '/allure-results']]
                    ])
                }
            }
        }
        
        
        stage('Publish Extent Report'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: false, 
                                  reportDir: 'reports', 
                                  reportFiles: 'BrianExecutionReport.html', 
                                  reportName: 'API HTML Extent Report', 
                                  reportTitles: ''])
            }
        }
        
        
         stage("Deploy to STAGE"){
            steps{
                echo("deploy to STAGE done")
            }
        }
        
        stage('Sanity Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/bahir007/MyRestAssuredFramework.git'
                    sh "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/testrunners/testng_sanity.xml"
                    
                }
            }
        }
        
        
         stage('Publish Extent Report after sanity'){
            steps{
                     publishHTML([allowMissing: false,
                                  alwaysLinkToLastBuild: false, 
                                  keepAll: false, 
                                  reportDir: 'reports', 
                                  reportFiles: 'BrianExecutionReport.html', 
                                  reportName: 'API HTML Extent Report', 
                                  reportTitles: ''])
            }
        }
        
        
        stage("Deploy to PROD"){
            steps{
                echo("deploy to PROD")
            }
        }
    }
}
