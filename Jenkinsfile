#!/usr/bin/env groovy

pipeline {
   agent any
   stages{
      stage('Build'){
         steps {
             echo 'Building the application ...'
         }
      }

      stage('test'){
         steps {
            echo 'Testing the application ...'
         }
      }
      stage('deploy'){
          steps {
            echo 'Deploying the application ...'
          }
      }
   }

   post {
     always {
        echo 'Send Notification'
     }
   }

}