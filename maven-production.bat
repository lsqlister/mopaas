set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m
mvn -Dmaven.test.skip=true -Pproduction clean package 