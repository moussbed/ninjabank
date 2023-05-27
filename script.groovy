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

def testApp(){
    echo 'Testing the application ...'
}

def deployApp(){
    echo 'Deploying the application ...'
}

return this
