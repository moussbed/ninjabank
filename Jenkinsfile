#!/usr/bin/env groovy

def gv

pipeline {
   agent any
   tools {
     jdk   'jdk-17'
     maven 'maven-3.9.2'
   }
   environment{
     SERVER_PUBLIC_IP = credentials('SERVER_PUBLIC_IP')
     DOCKER_COMPOSE_SECRET_FILE = credentials('DOCKER_COMPOSE_SECRET_FILE')
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
      stage('Build image'){
         steps {
              script{
                 gv.buildImage()
              }
         }
      }
      stage('Push image'){
        steps {
          script{
            gv.dockerLogin()
             gv.pushImage()
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