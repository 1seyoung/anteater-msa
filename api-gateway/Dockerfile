#베이스 이미지 설정
FROM openjdk:17-jdk-slim-buster as builder

#작업 디렉토리 설정
WORKDIR /app

#JAR 파일 복사
COPY build/libs/*.jar /app/app.jar

#실행 명령 설정
ENTRYPOINT ["java", "-jar", "/app/app.jar"]