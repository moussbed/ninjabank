#!/usr/bin/env groovy

def gv

pipeline {
   agent any
   stages{
      stage('Init') {
          steps {
               script {
                  gv=load "script.groovy"
               }
          }
      }
      stage('Build'){
         steps {
             gv.buildApp()
         }
      }

      stage('test'){
         steps {
            gv.testApp()
         }
      }
      stage('deploy'){
          steps {
            gv.deployApp()
          }
      }
   }

   post {
     always {
        echo 'Send Notification'
     }
   }

}