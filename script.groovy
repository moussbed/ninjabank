def incrementVersion(){
    echo 'Increment app version ...'
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1] // 0.0.1-SNAPSHOT
    version = version.split('-')[0] // 0.0.1
    def lastIndexOf = version.lastIndexOf(".") // 3
    def lastDigit = version.substring(lastIndexOf + 1) // 1
    lastDigit = lastDigit.toInteger() + 1 // 2
    version = version.substring(0, lastIndexOf+1) + lastDigit
    env.IMAGE_VERSION = version

    echo "${env.IMAGE_VERSION}"
}

def buildJar(){
    echo 'Building jar ... '
    sh 'mvn clean package'
}
def buildImage(){
    echo 'Building app docker ...'
    ['backend','gateway', 'security', 'transaction'].each{service ->
        sh "cd ${service} && docker build -t moussbed/service-${service}:${env.IMAGE_VERSION} ."
    }
}

def dockerLogin(){
    echo "Logging to docker hub repository ..."
    withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_CREDENTIALS',
            passwordVariable: 'PASS', usernameVariable: 'USER'
    )]){
        sh "docker login -u ${USER} -p ${PASS}"
    }

}
def pushImage(){
    echo 'Pushing docker Image to docker hub repository ...'
    withCredentials([usernamePassword(credentialsId: 'DOCKER_HUB_CREDENTIALS',
            passwordVariable: 'PASS', usernameVariable: 'USER'
    )]){
        ['backend','gateway', 'security', 'transaction'].each{service ->
            def imageName = "moussbed/service-${service}:${env.IMAGE_VERSION}"
            def latestImage =  "moussbed/service-${service}:latest"
            sh "docker tag ${imageName} ${latestImage}"
            sh "docker push ${imageName}"
            sh "docker push ${latestImage}"
        }
    }
}

def deployApp(){
    echo 'Deploying the application ...'
    def shellCmd = "bash ./server-cmds.sh ${env.IMAGE_VERSION}"
    def server = "root@${env.SERVER_PUBLIC_IP}"
    def directory = "/root"
    sshagent(['LINODE_SERVER_KEY']){
        sh "scp -o StrictHostKeyChecking=no server-cmds.sh ${server}:${directory}"
        sh "scp -o StrictHostKeyChecking=no init-user-db.sh ${server}:${directory}"
        sh "scp -o StrictHostKeyChecking=no ${env.DOCKER_COMPOSE_SECRET_FILE} ${server}:${directory}"
        sh "scp -o StrictHostKeyChecking=no docker-compose.yaml ${server}:${directory}"
        sh "ssh -o StrictHostKeyChecking=no ${server} ${shellCmd}"
    }
}

def updatePom(){
    echo 'Updating version numbers of modules in a multi-module maven project'
    sh "mvn versions:set -DnewVersion=${env.IMAGE_VERSION}"
    sh "mvn versions:commit"
}

def commitVersionUpdated(){
    echo 'Commit version updated ...'
    withCredentials([sshUserPrivateKey(credentialsId: 'GITHUB-PUSH-BUMP', keyFileVariable: 'keyFile')]){
        sh 'mkdir -p ~/.ssh && cp ${keyFile} ~/.ssh/id_rsa'
        sh 'git config user.email "jenkins@example.com"'
        sh 'git config user.name "jenkins"'

        sh 'git status'
        sh 'git branch'
        sh 'git config --list'

        sh 'git remote -v'
        sh 'git show-ref'

        // Change https:// to ssh so we can push
        // https://github.com/moussbed/ninjabank.git
        // git@github.com:moussbed/ninjabank.git
        sh 'git remote set-url origin `git remote get-url origin | sed -re "s%.+/([^/]+)/([^/]+)$%git@github.com:\\1/\\2%"`'
        sh 'git log -p -2'
        sh 'git add .'
        sh 'git commit -m "ci: version bump"'
        sh 'git push origin HEAD:jenkins-pipeline'
    }
}
return this
