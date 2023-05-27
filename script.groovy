def buildApp(){
    echo 'Building the application ... '
}

def testApp(){
    echo 'Testing the application ...'
}

def deployApp(){
    echo 'Deploying the application ...'
    ['backend', 'transaction'].each{ directory->
          print(directory)
    }
}

return this
