#!/usr/bin/env groovy

def gv

pipeline {
   agent any
   tools {
     maven 'maven-3.9.2'
   }
   stages{
      stage('Init') {
          steps {
               script {
                  gv=load "script.groovy"
               }
          }
      }
      stage('Increment version'){
        steps {
           script {
              gv.incrementVersion()
           }
        }
      }
      stage('Build jar'){
         steps {
           script{
             gv.buildJar()
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