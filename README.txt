
1.WAMP issues:

Your wamp should be installed in port 8078
after normal installation follow this link to change port number
http://stackoverflow.com/questions/8574332/how-to-change-port-number-for-apache-in-wamp

If port number is changed then phpmyadmin home page should have this title
http://localhost:8078/phpmyadmin/index.php


2.After wamp installation as said before you need to copy all the php files from "phpfiles" folder I send you to this folder
C:\wamp64\www

3.Before importing the project into android studio, open a normal project. just a dummy.
the build.gradle(Module:app) file under gradle scripts folder should look like this 

///build.gradle file looks like below:
apply plugin: 'com.android.application'

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.2"

    defaultConfig {
        applicationId "com.example.howkum.iumwsms"
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    compile 'com.android.support:appcompat-v7:26.0.0-alpha1'
} 
//// ends here

4. if all things matches then go to http://localhost:8078/phpmyadmin/index.php
5.go to import->choose file -> (select testdb.sql from the folder I send you)
6.press go.
7. check the database is imported or not.
8.If the database is imported then you are good to go.
9.make sure from avd manager you have already created a emulator to run the project.
9. from android studio import the project.
10.run...
