FROM java:8

MAINTAINER houtoo@foxmail.com

ADD star-sky-member.jar /home/star-sky-member/

ENV JASYPT_ENCRYPTOR_PASSWORD=OriginalMix
ENV APP_VERSION=1.0

EXPOSE 8080

CMD ["java", "-jar", "/home/star-sky-member/star-sky-member.jar"]