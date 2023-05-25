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
           script{
             gv.buildApp()
           }
         }
      }

      stage('test'){
         steps {
           script {
             gv.testApp()
           }

         }
      }
      stage('deploy'){
          steps {
            script {
              gv.deployApp()
            }
          }
      }
   }

   post {
     always {
        echo 'Send Notification'
     }
   }

}