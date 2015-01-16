# mopaas

mvn clean package -Pproduction即构建出生产环境需要的war包
 
mvn tomcat:redeploy -Ptest 即发布到测试环境

set MAVEN_OPTS=%MAVEN_OPTS% -XX:MaxPermSize=128m
mvn -Dmaven.test.skip=true clean package -Pproduction